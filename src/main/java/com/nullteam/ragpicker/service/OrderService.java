package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.Order;
import com.nullteam.ragpicker.model.OrderDetail;
import com.nullteam.ragpicker.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

public interface OrderService {

    @PreAuthorize("hasRole('ROLE_USER') and #userId == principal.id")
    Order createNewOrder(Integer userId, Integer addressId, List<OrderDetail> orderDetails, String remark);

    @Async
    void allotCollectorForOrder(Order order);

    Order getOneByOrderNo(String orderNo);

    @PreAuthorize("hasRole('ROLE_USER') and #user.id == principal.id")
    List<Order> getOrdersByUser(User user);

    List<Order> getAllOrdersByCollector(Collector collector);

    List<Order> getAllottedOrdersByCollector(Collector collector);

    List<Order> getCompletedOrderByUpdatedTime(Date start, Date end);

}
