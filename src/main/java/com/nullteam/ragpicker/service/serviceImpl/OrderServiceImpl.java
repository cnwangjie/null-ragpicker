package com.nullteam.ragpicker.service.serviceImpl;

import com.github.binarywang.wxpay.bean.request.WxEntPayRequest;
import com.github.binarywang.wxpay.bean.result.WxEntPayResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.nullteam.ragpicker.config.WxConfig;
import com.nullteam.ragpicker.model.*;
import com.nullteam.ragpicker.repository.OrderRepository;
import com.nullteam.ragpicker.service.AddressService;
import com.nullteam.ragpicker.service.CollectorService;
import com.nullteam.ragpicker.service.OrderService;
import com.nullteam.ragpicker.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final AddressService addressService;

    private final CollectorService collectorService;

    private final WxConfig wxConfig;

    private static final Long ALLOTT_FAILED_RETRY_WAIT_TIME = 300000L;
    private static final Long PAY_FAILED_RETRY_WAIT_TIME = 300000L;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserService userService,
                            AddressService addressService,
                            CollectorService collectorService,
                            WxConfig wxConfig) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.collectorService = collectorService;
        this.wxConfig = wxConfig;
    }

    @Override
    public Order createNewOrder(Integer userId, Integer addressId, List<OrderDetail> orderDetails, String remark) {
        User user = userService.getOneById(userId);
        if (user == null) return null;
        Address address = addressService.getOneById(addressId);
        if (address == null) return null;
        Order order = new Order();
        order.setRemark(remark);
        order.setUser(user);
        order.setStatus(Order.Status.INIT);
        order.setLocation(address.getLocation());
        order.setLocDetail(address.getDetail());
        order.setOrderDetail(orderDetails);
        String orderNo = (Long.toString(System.currentTimeMillis(), 36)
                + md5(String.valueOf(System.currentTimeMillis()))).substring(0, 30).toUpperCase();
        order.setOrderNo(orderNo);
        order = orderRepository.save(order);
        // TODO: perhaps should allow creating order if no collector in the location
        allotCollectorForOrder(order);
        return orderRepository.save(order);
    }

    @Override
    public void allotCollectorForOrder(Order order) {
        // TODO: allot collecotr & set order status
        if (order.getStatus() != Order.Status.INIT || order.getCollector() != null) return;
        List<Collector> sameLocationCollectors = collectorService.getAllByLocationIdEquals(order.getLocation());
        if (sameLocationCollectors.size() < 0) {
            try {
                Thread.sleep(ALLOTT_FAILED_RETRY_WAIT_TIME);
                allotCollectorForOrder(order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        sameLocationCollectors.sort(Comparator.comparingInt(collector -> getAllottedOrdersByCollector(collector).size()));
        Collector collector = sameLocationCollectors.get(0);
        order.setCollector(collector);
        order.setStatus(Order.Status.ALLOTTED);
        orderRepository.save(order);
    }

    @Override
    public Order completeOrder(Order order, Integer amount, List<OrderDetail> orderDetails) {
        order.setAmount(amount);
        order.setOrderDetail(orderDetails);
        order.setStatus(Order.Status.ALLOTTED);
        order = orderRepository.save(order);
        payOrder(order);
        return order;
    }


    private WxPayConfig getWxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(wxConfig.getWxAppid());
        wxPayConfig.setMchId(wxConfig.getWxMchId());
        wxPayConfig.setMchKey(wxConfig.getWxMchKey());
        wxPayConfig.setSubAppId(wxConfig.getWxSubAppId());
        wxPayConfig.setSubMchId(wxConfig.getWxSubMchId());
        wxPayConfig.setKeyPath(wxConfig.getWxKeyPath());
        return wxPayConfig;
    }

    @Bean
    public WxPayService getWxPayService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(getWxPayConfig());
        return wxPayService;
    }

    @Override
    public void payOrder(Order order) {
        // TODO: 微信企业支付接口
        WxEntPayRequest wxEntPayRequest = new WxEntPayRequest();
        wxEntPayRequest.setAppid(wxConfig.getWxAppid());
        wxEntPayRequest.setMchId(wxConfig.getWxMchId());
        wxEntPayRequest.setNonceStr(RandomStringUtils.randomAscii(32));
        wxEntPayRequest.setPartnerTradeNo(order.getOrderNo());
        wxEntPayRequest.setOpenid(order.getUser().getInfo().getWxid());
        wxEntPayRequest.setCheckName("NO_CHECK");
        wxEntPayRequest.setAmount(order.getAmount());
        wxEntPayRequest.setDescription("回收完成");
        wxEntPayRequest.setSpbillCreateIp("0.0.0.0");
        try {
            WxEntPayResult wxEntPayResult = getWxPayService().entPay(wxEntPayRequest);
            if ("SUCCESS".equals(wxEntPayResult.getResultCode().toUpperCase())
                && "SUCCESS".equals(wxEntPayResult.getReturnCode().toUpperCase())) {
                order.setStatus(Order.Status.PAID);
                orderRepository.save(order);
            } else {
                Thread.sleep(PAY_FAILED_RETRY_WAIT_TIME);
                payOrder(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order cancelOrderByUser(Order order) {
        order.setStatus(Order.Status.CANCELED_BY_USER);
        return orderRepository.save(order);
    }

    @Override
    public Order getOneByOrderNo(String orderNo) {
        return orderRepository.findOneByOrderNoEquals(orderNo);
    }

    private String md5(String str) {
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert hash != null;
        hash.update(str.getBytes());
        return new BigInteger(1, hash.digest()).toString(36);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user.getId());
    }

    @Override
    public List<Order> getAllOrdersByCollector(Collector collector) {
        return orderRepository.findByCollector(collector.getId());
    }

    @Override
    public List<Order> getAllottedOrdersByCollector(Collector collector) {
        return orderRepository.findByCollectorIdAndStatusIs(collector.getId(), Order.Status.ALLOTTED);
    }

    @Override
    public List<Order> getCompletedOrderByUpdatedTime(Date start, Date end) {
        return orderRepository.findByStatusIsAndUpdatedAtAfterAndUpdatedAtBefore(Order.Status.COMPLETED, start, end);
    }


}
