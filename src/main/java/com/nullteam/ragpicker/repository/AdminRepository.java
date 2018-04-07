package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Title: AdminRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: 管理员仓库</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
