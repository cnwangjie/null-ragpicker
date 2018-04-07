package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: CateController.java</p>
 * <p>Package: com.nullteam.ragpicker.controller.api</p>
 * <p>Description: 分类相关接口控制器</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
@RestController
@RequestMapping("/api")
public class CateController {

    private final CateService cateService;

    @Autowired
    public CateController(CateService cateService) {
        this.cateService = cateService;
    }

    @GetMapping("/cate")
    public ResponseEntity listAllCates() {
        return ResponseEntity.ok().body(cateService.getAll());
    }
}
