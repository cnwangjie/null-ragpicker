package com.nullteam.ragpicker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

    @Value("${wx.appid}")
    private String wxAppid;

    @Value("${wx.appsecret}")
    private String wxAppsecret;

    @Value("${wx.token}")
    private String wxToken;

    @Value("${wx.aeskey}")
    private String wxAeskey;

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxAppsecret() {
        return wxAppsecret;
    }

    public void setWxAppsecret(String wxAppsecret) {
        this.wxAppsecret = wxAppsecret;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public String getWxAeskey() {
        return wxAeskey;
    }

    public void setWxAeskey(String wxAeskey) {
        this.wxAeskey = wxAeskey;
    }

}
