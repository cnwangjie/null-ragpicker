package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Title: WxUserRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/19/18
 * @author WangJie <i@i8e.net>
 */
public interface WxUserRepository extends JpaRepository<WxUser, Integer> {
    WxUser findOneByWxid(String wxid);
}
