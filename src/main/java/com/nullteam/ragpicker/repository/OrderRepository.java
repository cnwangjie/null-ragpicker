package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrdersByUser(Integer userId);

    List<Order> findOrdersByCollector(Integer collectorId);

    List<Order> findOrdersByCollectorIdAndStatusIs(Integer collectorId, Integer status);

}
