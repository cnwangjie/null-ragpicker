package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Cate;

import java.util.List;

public interface CateService {

    List<Cate> getAll();

    Cate getOneById(Integer cateId);
}
