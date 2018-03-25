package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Title: UserRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
