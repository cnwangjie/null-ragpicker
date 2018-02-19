package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Cate;
import com.nullteam.ragpicker.repository.CateRepository;
import com.nullteam.ragpicker.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateServiceImpl implements CateService {

    private final CateRepository cateRepository;

    @Autowired
    public CateServiceImpl(CateRepository cateRepository) {
        this.cateRepository = cateRepository;
    }

    @Override
    public List<Cate> getAll() {
        return cateRepository.findAll();
    }

    @Override
    public Cate getOneById(Integer cateId) {
        return cateRepository.findOne(cateId);
    }
}
