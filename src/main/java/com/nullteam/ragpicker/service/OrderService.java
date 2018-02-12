package com.nullteam.ragpicker.service;


import com.nullteam.ragpicker.model.Order;

import java.util.List;

public interface OrderService {

    Order Create(Order order);

    void Update(Order order);

    Order Read(Integer id);

    void Delete(Integer id);

    List<Order> FindOrdersByUser(Integer userId);

    List<Order> FindOrdersByCollector(Integer collectorId);

    List<Order> FindAll();

}
