package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}/info")
    public ResponseEntity getUserInfo(@PathVariable Integer userId) {
        WxUser info = userService.getUserInfoByUserId(userId);
        if (info == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(info);
    }

    @PostMapping("/user/{id}/info/refresh")
    public ResponseEntity updateUserInfo(@PathVariable Integer id) {
        // TODO: refresh wechat user info
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body("{\"status\":\"SUCCESS\"}");
    }
}
