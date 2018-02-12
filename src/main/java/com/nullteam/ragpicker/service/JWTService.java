package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.User;

import java.util.Map;

public interface JWTService {

    String genUserToken(User user);

    String genCollectorToken(Collector collector);

    String getIdentityFromToken(String token);

    User getUserFromToken(String token);

    Collector getCollectorFromToken(String token);

}
