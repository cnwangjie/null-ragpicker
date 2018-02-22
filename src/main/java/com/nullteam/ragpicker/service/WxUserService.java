package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.WxUser;

public interface WxUserService {

    WxUser getOneByWxId(String wxid);

    WxUser update(WxUser wxUser);

}
