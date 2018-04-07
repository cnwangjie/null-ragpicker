package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Cate;

import java.util.List;

/**
 * <p>Title: CateService.java</p>
 * <p>Package: com.nullteam.ragpicker.service</p>
 * <p>Description: 分类service</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
public interface CateService {

    List<Cate> getAll();

    Cate getOneById(Integer cateId);
}
