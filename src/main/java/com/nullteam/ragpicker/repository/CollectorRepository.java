package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>Title: CollectorRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: 回收员仓库</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface CollectorRepository extends JpaRepository<Collector, Integer> {
    List<Collector> findByLocationEquals(Integer location);
}
