package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: OrderRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: 订单仓库</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Integer userId);

    List<Order> findByCollectorId(Integer collectorId);

    List<Order> findByCollectorIdAndStatusIs(Integer collectorId, Integer status);

    Order findOneByOrderNoEquals(String orderNo);

    List<Order> findByStatusIsAndUpdatedAtAfterAndUpdatedAtBefore(Integer status, Date start, Date end);

}
