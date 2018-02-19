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

    @Value("${wx.mchid}")
    private String wxMchId;

    @Value("${wx.mchkey}")
    private String wxMchKey;

    @Value("${wx.subappid}")
    private String wxSubAppId;

    @Value("${wx.submchid}")
    private String wxSubMchId;

    @Value("${wx.keypath}")
    private String wxKeyPath;

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

    public String getWxMchId() {
        return wxMchId;
    }

    public void setWxMchId(String wxMchId) {
        this.wxMchId = wxMchId;
    }

    public String getWxMchKey() {
        return wxMchKey;
    }

    public void setWxMchKey(String wxMchKey) {
        this.wxMchKey = wxMchKey;
    }

    public String getWxSubAppId() {
        return wxSubAppId;
    }

    public void setWxSubAppId(String wxSubAppId) {
        this.wxSubAppId = wxSubAppId;
    }

    public String getWxSubMchId() {
        return wxSubMchId;
    }

    public void setWxSubMchId(String wxSubMchId) {
        this.wxSubMchId = wxSubMchId;
    }

    public String getWxKeyPath() {
        return wxKeyPath;
    }

    public void setWxKeyPath(String wxKeyPath) {
        this.wxKeyPath = wxKeyPath;
    }

}
