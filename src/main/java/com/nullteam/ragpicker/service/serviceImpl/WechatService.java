package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.config.Config;
import com.nullteam.ragpicker.config.WxConfig;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WechatService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private Config config;

    public boolean verifyWechatToken(Map<String, String> params) throws NoSuchAlgorithmException {
        String token = wxConfig.getWxToken();
        String signature = params.get("signature");
        String timestamp = params.get("timestamp");
        String nonce = params.get("nonce");
        String rawString = Stream.of(token, timestamp, nonce).sorted().collect(Collectors.joining());
        return signature.equals(sha1(rawString));
    }

    public String sha1(String str) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance("SHA1");
        hash.update(str.getBytes());
        return new BigInteger(1, hash.digest()).toString(16);
    }

    public WxMpConfigStorage getWxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(wxConfig.getWxAppid());
        wxMpConfigStorage.setSecret(wxConfig.getWxAppsecret());
        wxMpConfigStorage.setToken(wxConfig.getWxToken());
        wxMpConfigStorage.setAesKey(wxConfig.getWxAeskey());
        return wxMpConfigStorage;
    }

    @Bean
    public WxMpService getWxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(this.getWxMpConfigStorage());
        return wxMpService;
    }

    public void createWxMenu() throws WxErrorException {
        WxMpService wxMpService = this.getWxMpService();

        // TODO: use json to configure wechat menu
        WxMenu wxMenu = new WxMenu();
        WxMenuButton btn0 = new WxMenuButton();
        btn0.setName("用户服务");
        WxMenuButton btn00 = new WxMenuButton();
        btn00.setName("快速下单");
        btn00.setType(WxConsts.MenuButtonType.VIEW);
        btn00.setUrl(wxMpService.oauth2buildAuthorizationUrl(config.getUrl() + "/web?identity=user&hashpath=/order/create", WxConsts.OAuth2Scope.SNSAPI_BASE, null));
        btn0.getSubButtons().add(btn00);
        WxMenuButton btn01 = new WxMenuButton();
        btn01.setName("个人中心");
        btn01.setType(WxConsts.MenuButtonType.VIEW);
        btn01.setUrl(wxMpService.oauth2buildAuthorizationUrl(config.getUrl() + "/web?identity=user", WxConsts.OAuth2Scope.SNSAPI_BASE, null));
        btn0.getSubButtons().add(btn01);
        wxMenu.getButtons().add(btn0);

        WxMenuButton btn1 = new WxMenuButton();
        btn1.setName("回收员专区");
        WxMenuButton btn10 = new WxMenuButton();
        btn10.setName("当前订单");
        btn10.setType(WxConsts.MenuButtonType.CLICK);
        btn10.setKey("current_order");
        btn1.getSubButtons().add(btn10);
        WxMenuButton btn11 = new WxMenuButton();
        btn11.setName("全部订单");
        btn11.setType(WxConsts.MenuButtonType.VIEW);
        btn11.setUrl(wxMpService.oauth2buildAuthorizationUrl(config.getUrl() + "/web?identity=collector&hashpath=/order", WxConsts.OAuth2Scope.SNSAPI_BASE, null));
        btn1.getSubButtons().add(btn11);
        wxMenu.getButtons().add(btn1);
        LoggerFactory.getLogger(this.getClass()).info(wxMenu.toJson());
        WxMpMenu currentMenu = wxMpService.getMenuService().menuGet();
        if (!wxMenu.equals(currentMenu)) wxMpService.getMenuService().menuCreate(wxMenu);

    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (config.isAutoUpdateMenu()) {
            try {
                this.createWxMenu();
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
    }
}
