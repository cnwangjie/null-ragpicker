package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Cate;
import com.nullteam.ragpicker.repository.CateRepository;
import com.nullteam.ragpicker.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateServiceImpl implements CateService {

    @Autowired
    private CateRepository cateRepository;


    @Override
    public List<Cate> FindAll() {
        return cateRepository.findAll();
    }
}
