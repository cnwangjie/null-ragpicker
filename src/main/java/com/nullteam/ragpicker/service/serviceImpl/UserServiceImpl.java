package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User Create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void Update(User user) {
        userRepository.save(user);
    }

    @Override
    public User Read(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public void Delete(Integer id) {
        userRepository.delete(id);
    }

}
