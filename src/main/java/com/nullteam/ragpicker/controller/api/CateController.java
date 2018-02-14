package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CateController {

    @Autowired
    private CateService cateService;

    @GetMapping("/cate")
    public ResponseEntity listAllCates() {
        return ResponseEntity.ok().body(cateService.getAll());
    }
}