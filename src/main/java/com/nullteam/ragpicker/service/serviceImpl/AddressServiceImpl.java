package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.repository.AddressRepository;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Address> findAllByUserId(Integer userId) {
        return addressRepository.findAllByUserId(userId);
    }

    @Override
    public Address addAddressForUserById(Address address, Integer userId) {
        address.setUser(userRepository.findOne(userId));
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getOneById(Integer id) {
        return addressRepository.findOne(id);
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }

    public void validate(Address address) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(address, address.getClass().getName());
    }

}
