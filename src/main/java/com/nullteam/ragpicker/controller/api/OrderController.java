package com.nullteam.ragpicker.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullteam.ragpicker.model.*;
import com.nullteam.ragpicker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: OrderController.java</p>
 * <p>Package: com.nullteam.ragpicker.controller.api</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
@RestController
@ControllerAdvice
@RequestMapping("/api")
public class OrderController {

    private final UserService userService;

    private final CollectorService collectorService;

    private final AddressService addressService;

    private final OrderService orderService;

    private final CateService cateService;

    @Autowired
    public OrderController(UserService userService,
                           CollectorService collectorService,
                           AddressService addressService,
                           OrderService orderService,
                           CateService cateService) {
        this.userService = userService;
        this.collectorService = collectorService;
        this.addressService = addressService;
        this.orderService = orderService;
        this.cateService = cateService;
    }

    /**
     * 获取用户个人订单
     *
     */
    @GetMapping("/user/{userId}/order")
    public ResponseEntity listOrdersByUser(@PathVariable Integer userId) {
        User user = userService.getOneById(userId);
        if (user == null) return ResponseEntity.notFound().build();
        List<Order> orders = orderService.getOrdersByUser(user);
        // TODO: paginate & filter field
        return ResponseEntity.ok().body(orders);
    }

    /**
     * 创建订单
     *
     */
    @PostMapping("/user/{userId}/order")
    public ResponseEntity createOrder(@PathVariable Integer userId,
                                      @RequestParam(name = "address_id") Integer addressId,
                                      @RequestParam(defaultValue = "") String remark,
                                      @RequestParam(name = "order_details") String orderDetailsJsonStr) throws IOException {
        // TODO: 创建订单业务逻辑
        User user = userService.getOneById(userId);
        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Address address = addressService.getOneById(addressId);
        if (address == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<HashMap<String, Integer>> list = new ObjectMapper().readValue(orderDetailsJsonStr, List.class);
        for (HashMap<String, Integer> item : list) {
            OrderDetail orderDetail = new OrderDetail();
            Cate cate = cateService.getOneById(item.get("cate_id"));
            if (cate == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            Integer sum = item.get("sum");
            if (sum < 1 || sum > 1000) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            orderDetail.setCate(cate);
            orderDetail.setSum(sum);
            orderDetails.add(orderDetail);
        }
        Order order = orderService.createNewOrder(userId, addressId, orderDetails, remark);
        if (order == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(order);
    }

    /**
     * 修改订单
     *
     */
//    @PostMapping("/user/{userId}")
//    public ResponseEntity updateOrder(@PathVariable Integer userId,
//                                      @ModelAttribute Order order) {
//        // TODO: 更新订单业务逻辑
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping("/order/{orderNo}/cancel")
    public ResponseEntity deleteOrder(@PathVariable String orderNo) {
        Order order = orderService.getOneByOrderNo(orderNo);
        if (order == null) return ResponseEntity.notFound().build();
        if (order.getStatus() != Order.Status.INIT) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        order = orderService.cancelOrderByUser(order);
        if (order.getStatus() != Order.Status.CANCELED_BY_USER) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body("{\"status\":\"success\"}");
    }

    @GetMapping("/collector/{collectorId}/order")
    public ResponseEntity listOrdersByCollector(@PathVariable Integer collectorId) {
        Collector collector = collectorService.getOneById(collectorId);
        if (collector == null) return ResponseEntity.notFound().build();
        List<Order> orders = orderService.getAllOrdersByCollector(collector);
        // TODO: paginate & transform !!!
        return ResponseEntity.ok(orders);
    }


    @PostMapping("/order/{orderNo}/complete")
    public ResponseEntity completeOrder(@PathVariable String orderNo,
                                        @RequestParam(name = "order_details") String orderDetailsJsonStr) throws IOException {
        Order order = orderService.getOneByOrderNo(orderNo);
        if (order == null) return ResponseEntity.notFound().build();
        List<OrderDetail> orderDetails = new ArrayList<>();
        Integer amount = 0;
        try {
            List<HashMap<String, Integer>> list = new ObjectMapper().readValue(orderDetailsJsonStr, List.class);
            for (HashMap<String, Integer> item : list) {
                OrderDetail orderDetail = new OrderDetail();
                Cate cate = cateService.getOneById(item.get("cate_id"));
                if (cate == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                Integer sum = item.get("sum");
                if (sum < 1 || sum > 1000) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                Integer price = item.get("price");
                if (price < 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                orderDetail.setCate(cate);
                orderDetail.setSum(sum);
                orderDetail.setPrice(price);
                orderDetails.add(orderDetail);
                amount += sum * price;
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (Order.Status.ALLOTTED != order.getStatus()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        order = orderService.completeOrder(order, amount, orderDetails);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order")
    public ResponseEntity getAllOrders(@RequestParam Date start,
                                       @RequestParam Date end) {
        List<Order> orders = orderService.getCompletedOrderByUpdatedTime(start, end);
        // TODO: authorize and transform
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{orderNo}")
    public ResponseEntity getOrderDetail(@PathVariable String orderNo) {
        Order order = orderService.getOneByOrderNo(orderNo);
        if (order == null) return ResponseEntity.notFound().build();
        // TODO: transform
        return ResponseEntity.ok(order);
    }
}
