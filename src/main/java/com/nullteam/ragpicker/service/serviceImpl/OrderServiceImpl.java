package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.*;
import com.nullteam.ragpicker.repository.OrderRepository;
import com.nullteam.ragpicker.service.AddressService;
import com.nullteam.ragpicker.service.CollectorService;
import com.nullteam.ragpicker.service.OrderService;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CollectorService collectorService;

    private static final Long ALLOTT_FAILED_RETRY_WAIT_TIME = 300000L;

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
        return order;
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
