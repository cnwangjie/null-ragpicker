package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.User;

/**
 * <p>Title: JWTService.java</p>
 * <p>Package: com.nullteam.ragpicker.service</p>
 * <p>Description: JWTservice</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/01/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
public interface JWTService {

    String genUserToken(User user);

    String genCollectorToken(Collector collector);

    String getIdentityFromToken(String token);

    User getUserFromToken(String token);

    Collector getCollectorFromToken(String token);

}
