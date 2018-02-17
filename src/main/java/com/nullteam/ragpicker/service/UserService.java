package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

    User getOneById(Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') and #userId == principal.id")
    WxUser getUserInfoByUserId(Integer userId);

    User create(User user);

}
