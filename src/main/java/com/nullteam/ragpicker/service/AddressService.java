package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Address;

import java.util.List;

public interface AddressService {

    List<Address> FindAllByUserId(Integer userId);

    Address Create(Address address);

    void Update(Address address);

    Address Read(Integer id);

    void Delete(Integer id);

}
