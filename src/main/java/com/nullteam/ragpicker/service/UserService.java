package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.User;

public interface UserService {

    User Create(User user);

    void Update(User user);

    User Read(Integer id);

    void Delete(Integer id);

}
