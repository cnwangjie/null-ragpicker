package com.nullteam.ragpicker.controller;

import com.nullteam.ragpicker.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
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

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
@RequestMapping("/wechat/protal")
public class WechatProtalController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;

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
}
