package com.nullteam.ragpicker.controller;

import com.nullteam.ragpicker.service.WechatService;
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

    @Autowired
    private WechatService wechatService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, params = {"signature", "echostr", "timestamp", "nonce"})
    public ResponseEntity wechatServerTokenVerify(@RequestParam Map<String, String> params) throws NoSuchAlgorithmException {
        if (wechatService.verifyWechatToken(params))
            return new ResponseEntity(params.get("echostr").toString(), HttpStatus.OK);

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
