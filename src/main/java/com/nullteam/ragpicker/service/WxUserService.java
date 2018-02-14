package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.WxUser;

public interface WxUserService {

    WxUser getOneByWxId(String wxid);

    WxUser Save(WxUser wxUser);

}
