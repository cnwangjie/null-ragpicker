package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(Integer userId);

    List<Order> findByCollector(Integer collectorId);

    List<Order> findByCollectorIdAndStatusIs(Integer collectorId, Integer status);

    Order findOneByOrderNoEquals(String orderNo);

    List<Order> findByStatusIsAndUpdatedAtAfterAndUpdatedAtBefore(Integer status, Date start, Date end);

}
