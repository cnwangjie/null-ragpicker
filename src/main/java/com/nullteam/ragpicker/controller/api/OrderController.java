package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.Order;
import com.nullteam.ragpicker.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
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
     * @param id
     * @return
     */
    @GetMapping("/user/{id}/order")
    public ResponseEntity listOrdersByUser(@PathVariable Integer id) {
        List<Order> orders = orderService.FindOrdersByUser(id);
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    /**
     * 创建订单
     *
     * @param id
     * @param order
     * @return
     */
    @PostMapping("/user/{id}/order")
    public ResponseEntity createOrder(@PathVariable Integer id,
                                      @ModelAttribute Order order) {
        return new ResponseEntity(orderService.Create(order), HttpStatus.OK);
    }

    /**
     * 修改订单
     *
     * @param id
     * @param order
     * @return
     */
    @PostMapping("/user/{id}")
    public ResponseEntity updateOrder(@PathVariable Integer id,
                                      @ModelAttribute Order order) {
        orderService.Update(order);
        return new ResponseEntity(orderService.Read(id), HttpStatus.OK);
    }

    @PostMapping("/user/{id}/cancel")
    public ResponseEntity deleteOrder(@PathVariable Integer id) {
        orderService.Delete(id);
        return null;
    }

    @GetMapping("/collector/{id}/order")
    public ResponseEntity listOrdersByCollector(@PathVariable Integer id) {
        return new ResponseEntity(orderService.FindOrdersByCollector(id), HttpStatus.OK);
    }

    @PostMapping("/order/{id}/complete")
    public ResponseEntity completeOrder(@PathVariable Integer id) {
        Order order = orderService.Read(id);
        order.setStatus(2);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity getAllOrders() {
        return new ResponseEntity(orderService.FindAll(), HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity getOrderDetail(@PathVariable Integer id) {
        return new ResponseEntity(orderService.Read(id), HttpStatus.OK);
    }
}
