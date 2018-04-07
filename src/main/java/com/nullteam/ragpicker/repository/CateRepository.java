package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Cate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Title: CateRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: 分类仓库</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface CateRepository extends JpaRepository<Cate, Integer> {
}
