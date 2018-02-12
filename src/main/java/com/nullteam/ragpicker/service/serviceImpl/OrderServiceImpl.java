package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Order;
import com.nullteam.ragpicker.repository.OrderRepository;
import com.nullteam.ragpicker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order Create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void Update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order Read(Integer id) {
        return orderRepository.findOne(id);
    }

    @Override
    public void Delete(Integer id) {
        orderRepository.delete(id);
    }

    @Override
    public List<Order> FindOrdersByUser(Integer userId) {
        return orderRepository.findOrdersByUser(userId);
    }

    @Override
    public List<Order> FindOrdersByCollector(Integer collectorId) {
        return orderRepository.findOrdersByCollector(collectorId);
    }

    @Override
    public List<Order> FindAll() {
        return orderRepository.findAll();
    }


}
