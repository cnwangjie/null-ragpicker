package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.model.WxUser;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public WxUser getUserInfoByUserId(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user == null) return null;
        return user.getInfo();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }


}
