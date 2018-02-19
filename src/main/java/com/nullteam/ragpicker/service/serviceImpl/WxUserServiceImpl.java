package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.repository.WxUserRepository;
import com.nullteam.ragpicker.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl implements WxUserService {

    private final WxUserRepository wxUserRepository;

    @Autowired
    public WxUserServiceImpl(WxUserRepository wxUserRepository) {
        this.wxUserRepository = wxUserRepository;
    }

    @Override
    public WxUser getOneByWxId(String wxid) {
        return wxUserRepository.findOneByWxid(wxid);
    }

    @Override
    public WxUser Save(WxUser wxUser) {
        return wxUserRepository.save(wxUser);
    }
}
