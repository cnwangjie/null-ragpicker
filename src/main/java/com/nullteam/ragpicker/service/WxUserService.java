package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.WxUser;

/**
 * <p>Title: WxUserService.java</p>
 * <p>Package: com.nullteam.ragpicker.service</p>
 * <p>Description: 微信用户service</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
public interface WxUserService {

    WxUser getOneByWxId(String wxid);

    WxUser update(WxUser wxUser);

}
