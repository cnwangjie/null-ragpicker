package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title: UserServiceImpl.java</p>
 * <p>Package: package com.nullteam.ragpicker.service.serviceImpl;</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getOneById(Integer userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public WxUser getUserInfoByUserId(Integer userId) {
        User user = this.getOneById(userId);
        if (user == null) return null;
        return user.getInfo();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }


}
