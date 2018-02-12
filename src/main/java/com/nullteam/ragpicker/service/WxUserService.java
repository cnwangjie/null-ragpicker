package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.WxUser;

public interface WxUserService {

    WxUser FindOneByWxid(String wxid);

    WxUser Save(WxUser wxUser);

}
