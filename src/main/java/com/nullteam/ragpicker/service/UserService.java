package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * <p>Title: UserService.java</p>
 * <p>Package: com.nullteam.ragpicker.service</p>
 * <p>Description: 用户service</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
public interface UserService {

    User getOneById(Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') and #userId == principal.id")
    WxUser getUserInfoByUserId(Integer userId);

    User create(User user);

}
