package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.config.Config;
import com.nullteam.ragpicker.config.WxConfig;
import com.nullteam.ragpicker.handler.CurrentOrderBtnClickEventHandler;
import com.nullteam.ragpicker.handler.SubscribeEventHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Title: WechatService.java</p>
 * <p>Package: package com.nullteam.ragpicker.service.serviceImpl;</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
@Service
public class WechatService implements ApplicationListener<ApplicationReadyEvent> {

    private final WxConfig wxConfig;

    private final Config config;

    private CurrentOrderBtnClickEventHandler currentOrderBtnClickEventHandler;

    private SubscribeEventHandler subscribeEventHandler;

    private static final String CURRENT_ORDER_EVENT_KEY = "current_order";

    @Autowired
    public WechatService(WxConfig wxConfig, Config config) {
        this.wxConfig = wxConfig;
        this.config = config;
    }

    @Autowired
    public void setCurrentOrderBtnClickEventHandler(CurrentOrderBtnClickEventHandler currentOrderBtnClickEventHandler) {
        this.currentOrderBtnClickEventHandler = currentOrderBtnClickEventHandler;
    }

    @Autowired
    public void setSubscribeEventHandler(SubscribeEventHandler subscribeEventHandler) {
        this.subscribeEventHandler = subscribeEventHandler;
    }

    public boolean verifyWechatToken(Map<String, String> params) throws NoSuchAlgorithmException {
        String token = wxConfig.getWxToken();
        String signature = params.get("signature");
        String timestamp = params.get("timestamp");
        String nonce = params.get("nonce");
        String rawString = Stream.of(token, timestamp, nonce).sorted().collect(Collectors.joining());
        return signature.equals(sha1(rawString));
    }

    private String sha1(String str) throws NoSuchAlgorithmException {
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

    private static String join(String path0, String path1) {
        return new File(path0, path1).toString();
    }

    public String buildOauthUrl(String identity, String hashpath, String scope, String state) {
        return this.getWxMpService().oauth2buildAuthorizationUrl(config.getUrl() + "/web?" + (null != identity ? ("identity=" + identity + "&") : "") + "hashpath=" + hashpath, scope, state);
    }

    public String buildOauthUrl(String identity, String hashpath, String scope) {
        return buildOauthUrl(identity, hashpath, scope, null);
    }

    public String buildOauthUrl(String identity, String hashpath) {
        return buildOauthUrl(identity, hashpath, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
    }

    public String buildOauthUrl(String identity) {
        return buildOauthUrl(identity, null, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
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
        btn00.setUrl(buildOauthUrl("user", "/order/create"));
        btn0.getSubButtons().add(btn00);
        WxMenuButton btn01 = new WxMenuButton();
        btn01.setName("个人中心");
        btn01.setType(WxConsts.MenuButtonType.VIEW);
        btn01.setUrl(buildOauthUrl("user", "/home"));
        btn0.getSubButtons().add(btn01);
        wxMenu.getButtons().add(btn0);

        WxMenuButton btn1 = new WxMenuButton();
        btn1.setName("回收员专区");
        WxMenuButton btn10 = new WxMenuButton();
        btn10.setName("当前订单");
        btn10.setType(WxConsts.MenuButtonType.CLICK);
        btn10.setKey(CURRENT_ORDER_EVENT_KEY);
        btn1.getSubButtons().add(btn10);
        WxMenuButton btn11 = new WxMenuButton();
        btn11.setName("全部订单");
        btn11.setType(WxConsts.MenuButtonType.VIEW);
        btn11.setUrl(buildOauthUrl("collector", "/order"));
        btn1.getSubButtons().add(btn11);
        wxMenu.getButtons().add(btn1);
        LoggerFactory.getLogger(this.getClass()).info(wxMenu.toJson());
        WxMpMenu currentMenu = wxMpService.getMenuService().menuGet();
        //noinspection EqualsBetweenInconvertibleTypes
        if (!wxMenu.equals(currentMenu)) wxMpService.getMenuService().menuCreate(wxMenu);

    }

    @Bean
    public WxMpMessageRouter getWxMpMessageRouter() {
        return new WxMpMessageRouter(this.getWxMpService())
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeEventHandler)
                .end()
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK)
                .eventKey(CURRENT_ORDER_EVENT_KEY)
                .handler(currentOrderBtnClickEventHandler)
                .end();
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
