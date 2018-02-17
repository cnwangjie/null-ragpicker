package com.nullteam.ragpicker.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullteam.ragpicker.model.*;
import com.nullteam.ragpicker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@ControllerAdvice
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private CollectorService collectorService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CateService cateService;

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
        Order order = new Order();
        order.setRemark(remark);
        order.setUser(user);
        order.setStatus(Order.Status.INIT);
        order.setLocation(address.getLocation());
        order.setLocDetail(address.getDetail());
        order.setOrderDetail(orderDetails);
        return null;
    }

    /**
     * 修改订单
     *
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity updateOrder(@PathVariable Integer userId,
                                      @ModelAttribute Order order) {
        // TODO: 更新订单业务逻辑
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/{userId}/cancel")
    public ResponseEntity deleteOrder(@PathVariable Integer userId) {
        // TODO: verify status & change status
        return null;
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
                                        @RequestParam Integer amount,
                                        @RequestParam String orderDetailsJsonStr) {
        Order order = orderService.getOneByOrderNo(orderNo);
        if (order == null) return ResponseEntity.notFound().build();
        // TODO:
        order.setStatus(Order.Status.COMPLETED);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity getAllOrders(@RequestParam Date start,
                                       @RequestParam Date end) {
        // TODO: authorize and transform
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity getOrderDetail(@PathVariable Integer id) {
        // TODO: authorize and transform
        return ResponseEntity.ok().build();
    }
}
