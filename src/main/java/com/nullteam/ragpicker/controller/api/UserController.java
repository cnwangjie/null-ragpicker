package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}/info")
    public ResponseEntity getUserInfo(@PathVariable Integer id) {
        User user = userService.Read(id);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/user/{id}/info/refresh")
    public ResponseEntity updateUserInfo(@PathVariable Integer id,
                                         @ModelAttribute User user) {
        userService.Update(user);
        return new ResponseEntity(userService.Read(id), HttpStatus.OK);
    }
}
