package com.nullteam.ragpicker.controller;

import com.nullteam.ragpicker.service.serviceImpl.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * <p>Title: WechatProtalController.java</p>
 * <p>Package: com.nullteam.ragpicker.controller</p>
 * <p>Description: 微信接口控制器</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/27/18
 * @author WangJie <i@i8e.net>
 * @author Robin <robinchow8991@gmail.com>
 */
@Controller
@RequestMapping("/wechat/protal")
public class WechatProtalController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WechatService wechatService;

    @Autowired
    public WechatProtalController(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, params = {"signature", "echostr", "timestamp", "nonce"})
    public ResponseEntity wechatServerTokenVerify(@RequestParam Map<String, String> params) throws NoSuchAlgorithmException, WxErrorException {
        if (wechatService.verifyWechatToken(params)) {
            logger.info("========================================================");
            logger.info("wechat token verified success, try to update custom menu");
            try {
                wechatService.createWxMenu();
                logger.info("custom menu updated!");
            } catch (WxErrorException e) {
                logger.warn("custom menu update failed: " + e.getError().toString());
            }
            logger.info("========================================================");
            return ResponseEntity.ok(params.get("echostr"));
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "")
    public void WechatMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // refer: https://github.com/Wechat-Group/weixin-java-tools-springmvc/blob/master/src/main/java/com/github/weixin/demo/controller/CoreController.java#L76-L96
        if ("aes".equals(req.getParameter("encrypt_type"))) {
            String msgSignature = req.getParameter("msg_signature");
            String nonce = req.getParameter("nonce");
            String timestamp = req.getParameter("timestamp");
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                    req.getInputStream(), wechatService.getWxMpConfigStorage(), timestamp, nonce,
                    msgSignature);
            WxMpXmlOutMessage outMessage = wechatService.getWxMpMessageRouter().route(inMessage);
            if (outMessage != null)
                res.getWriter().write(outMessage.toEncryptedXml(wechatService.getWxMpConfigStorage()));
        } else {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(req.getInputStream());
            WxMpXmlOutMessage outMessage = wechatService.getWxMpMessageRouter().route(inMessage);
            if (outMessage != null)
                res.getWriter().write(outMessage.toXml());
        }
    }
}
