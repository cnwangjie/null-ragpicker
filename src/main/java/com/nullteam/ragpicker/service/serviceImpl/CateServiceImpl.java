package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Cate;
import com.nullteam.ragpicker.repository.CateRepository;
import com.nullteam.ragpicker.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: CateServiceImpl.java</p>
 * <p>Package: package com.nullteam.ragpicker.service.serviceImpl;</p>
 * <p>Description: 分类service实现</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
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
