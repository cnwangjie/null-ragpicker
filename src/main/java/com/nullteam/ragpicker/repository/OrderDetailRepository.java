package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Title: OrderDetailRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: 订单详情仓库</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
