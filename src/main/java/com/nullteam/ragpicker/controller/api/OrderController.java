package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.Order;
import com.nullteam.ragpicker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取用户个人订单
     *
     */
    @GetMapping("/user/{userId}/order")
    public ResponseEntity listOrdersByUser(@PathVariable Integer userId) {
        List<Order> orders = orderService.FindOrdersByUser(userId);
        // TODO: paginate & filter field
        return ResponseEntity.ok().body(orders);
    }

    /**
     * 创建订单
     *
     */
    @PostMapping("/user/{id}/order")
    public ResponseEntity createOrder(@PathVariable Integer id,
                                      @ModelAttribute Order order) {
        // TODO: 创建订单业务逻辑
        return new ResponseEntity(orderService.Create(order), HttpStatus.OK);
    }

    /**
     * 修改订单
     *
     */
    @PostMapping("/user/{id}")
    public ResponseEntity updateOrder(@PathVariable Integer id,
                                      @ModelAttribute Order order) {
        orderService.Update(order);
        // TODO: 更新订单业务逻辑
        return new ResponseEntity(orderService.Read(id), HttpStatus.OK);
    }

    @PostMapping("/user/{id}/cancel")
    public ResponseEntity deleteOrder(@PathVariable Integer id) {
        // TODO: verify status & change status
        return null;
    }

    @GetMapping("/collector/{id}/order")
    public ResponseEntity listOrdersByCollector(@PathVariable Integer id) {
        // TODO: paginate & transform
        return new ResponseEntity(orderService.FindOrdersByCollector(id), HttpStatus.OK);
    }

    @PostMapping("/order/{id}/complete")
    public ResponseEntity completeOrder(@PathVariable Integer id) {
        Order order = orderService.Read(id);
        // TODO:
        order.setStatus(Order.Status.COMPLETED);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity getAllOrders() {
        // TODO: authorize and transform
        return new ResponseEntity(orderService.FindAll(), HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity getOrderDetail(@PathVariable Integer id) {
        // TODO: authorize and transform
        return new ResponseEntity(orderService.Read(id), HttpStatus.OK);
    }
}
