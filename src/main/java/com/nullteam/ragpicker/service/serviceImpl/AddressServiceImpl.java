package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.repository.AddressRepository;
import com.nullteam.ragpicker.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> FindAllByUserId(Integer userId) {
        return addressRepository.findAllByUserId(userId);
    }

    @Override
    public Address Create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void Update(Address address) {
        addressRepository.save(address);
    }

    @Override
    public Address Read(Integer id) {
        return addressRepository.findOne(id);

    }

    @Override
    public void Delete(Integer id) {
        addressRepository.delete(id);
    }

}
