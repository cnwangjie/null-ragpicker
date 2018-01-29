package com.nullteam.ragpicker.controller;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.repository.CollectorRepository;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.repository.WxUserRepository;
import com.nullteam.ragpicker.service.WechatService;
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
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.net.URI;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WxUserRepository wxUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    @ResponseBody
    @RequestMapping(value = "/:identity/:hashpath*", method = RequestMethod.GET)
    public ResponseEntity webpage(HttpServletResponse res,
                                  @PathParam("identity") String identity,
                                  @PathParam("hashpath") String hashpath,
                                  @RequestParam("code") String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wechatService.getWxMpService().oauth2getAccessToken(code);
        WxMpUser wxMpUser = wechatService.getWxMpService().oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        String openid = wxMpUser.getOpenId();
        WxUser wxUser = wxUserRepository.findOneByWxid(openid);
        if (wxUser == null) {
            if (wxMpUser.getNickname() == null) {;
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "").build();
            }

            wxUser = new WxUser();
            wxUser.setNickname(wxMpUser.getNickname());
            wxUser.setWxid(wxMpUser.getOpenId());
            wxUser.setAvatar(wxMpUser.getHeadImgUrl());
            wxUserRepository.save(wxUser);
        }

        switch (identity) {
            case "user":
                if (wxUser.getUser() == null) {
                    User user = new User();
                    user.setInfo(wxUser);
                    userRepository.save(user);
                }
                // gen jwt token
                break;
            case "collector":
                if (wxUser.getCollector() == null) {
                    Collector collector = new Collector();
                    collector.setInfo(wxUser);
                    collectorRepository.save(collector);
                    hashpath = "info/edit";
                }
                // gen jwt token
                break;
            default:
                return ResponseEntity.notFound().build();
        }
        res.addCookie(new Cookie("user-token", "jwtotken"));
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/web/#/" + identity + "/" + hashpath)).build();
    }
}