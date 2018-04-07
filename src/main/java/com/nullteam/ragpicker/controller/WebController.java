package com.nullteam.ragpicker.controller;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.service.CollectorService;
import com.nullteam.ragpicker.service.JWTService;
import com.nullteam.ragpicker.service.UserService;
import com.nullteam.ragpicker.service.WxUserService;
import com.nullteam.ragpicker.service.serviceImpl.WechatService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * <p>Title: WebController.java</p>
 * <p>Package: com.nullteam.ragpicker.controller</p>
 * <p>Description: WEB相关接口控制器</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/30/18
 * @author WangJie <i@i8e.net>
 * @author Robin <robinchow8991@gmail.com>
 */
@Controller
@RequestMapping("/web")
public class WebController {

    private final WechatService wechatService;

    private final JWTService jwtService;

    private final WxUserService wxUserService;

    private final UserService userService;

    private final CollectorService collectorService;

    @Autowired
    public WebController(WechatService wechatService,
                         JWTService jwtService,
                         WxUserService wxUserService,
                         UserService userService,
                         CollectorService collectorService) {
        this.wechatService = wechatService;
        this.jwtService = jwtService;
        this.wxUserService = wxUserService;
        this.userService = userService;
        this.collectorService = collectorService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity webpage(HttpServletRequest req,
                                  HttpServletResponse res,
                                  @RequestParam("identity") String identity,
                                  @RequestParam(name = "hashpath", defaultValue = "/", required = false) String hashpath,
                                  @RequestParam("code") String code) throws WxErrorException {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wechatService.getWxMpService().oauth2getAccessToken(code);
        String openid = wxMpOAuth2AccessToken.getOpenId();
        WxUser wxUser = wxUserService.getOneByWxId(openid);
        if (wxUser == null) {
            try {
                WxMpUser wxMpUser = wechatService.getWxMpService().oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                wxUser = new WxUser();
                wxUser.setNickname(wxMpUser.getNickname());
                wxUser.setWxid(wxMpUser.getOpenId());
                wxUser.setAvatar(wxMpUser.getHeadImgUrl());
                wxUser.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
                wxUserService.update(wxUser);
            } catch (Exception e) {
                String userInfoOAuthPath = wechatService.buildOauthUrl(identity, hashpath, WxConsts.OAuth2Scope.SNSAPI_USERINFO);
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", userInfoOAuthPath).build();
            }
        }
        String jwtToken;
        switch (identity) {
            case "user":
                if (wxUser.getUser() == null) {
                    User user = new User();
                    user.setInfo(wxUser);
                    userService.create(user);
                    wxUser.setUser(user);
                }
                jwtToken = jwtService.genUserToken(wxUser.getUser());
                break;
            case "collector":
                if (wxUser.getCollector() == null) {
                    Collector collector = new Collector();
                    collector.setInfo(wxUser);
                    collectorService.create(collector);
                    wxUser.setCollector(collector);
                    hashpath = "info/edit";
                }
                jwtToken = jwtService.genCollectorToken(wxUser.getCollector());
                break;
            default:
                return ResponseEntity.notFound().build();
        }
        res.addCookie(new Cookie("jwt-token", jwtToken));
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/web/index.html#/" + identity + (hashpath.charAt(0) == '/' ? "" : "/") + hashpath))
                .build();
    }
}
