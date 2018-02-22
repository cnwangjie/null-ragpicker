package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.service.UserService;
import com.nullteam.ragpicker.service.WxUserService;
import com.nullteam.ragpicker.service.serviceImpl.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final WechatService wechatService;

    private final WxUserService wxUserService;

    @Autowired
    public UserController(UserService userService,
                          WechatService wechatService,
                          WxUserService wxUserService) {
        this.userService = userService;
        this.wechatService = wechatService;
        this.wxUserService = wxUserService;
    }

    @GetMapping("/user/{userId}/info")
    public ResponseEntity getUserInfo(@PathVariable Integer userId) {
        WxUser info = userService.getUserInfoByUserId(userId);
        if (info == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(info);
    }

    @PostMapping("/user/{userId}/info/refresh")
    public ResponseEntity updateUserInfo(@PathVariable Integer userId) {
        User user = userService.getOneById(userId);
        if (user == null) return ResponseEntity.notFound().build();
        WxMpService wxMpService = wechatService.getWxMpService();
        WxUser wxUser = user.getInfo();
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxUser.getRefreshToken());
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            wxUser.setNickname(wxMpUser.getNickname());
            wxUser.setAvatar(wxMpUser.getHeadImgUrl());
            wxUser.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
            wxUserService.update(wxUser);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body("{\"status\":\"SUCCESS\"}");
        } catch (WxErrorException e) {
            e.printStackTrace();
            // TODO: return OAuth URL
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body("{\"status\":\"ERROR\"}");
        }
    }
}
