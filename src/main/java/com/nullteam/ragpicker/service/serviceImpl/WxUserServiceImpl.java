package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.repository.WxUserRepository;
import com.nullteam.ragpicker.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title: WxUserServiceImpl.java</p>
 * <p>Package: package com.nullteam.ragpicker.service.serviceImpl;</p>
 * <p>Description: 微信用户service</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
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
    public WxUser update(WxUser wxUser) {
        return wxUserRepository.save(wxUser);
    }
}
