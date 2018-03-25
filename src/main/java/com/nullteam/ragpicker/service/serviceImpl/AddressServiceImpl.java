package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.repository.AddressRepository;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

/**
 * <p>Title: AddressServiceImpl.java</p>
 * <p>Package: package com.nullteam.ragpicker.service.serviceImpl;</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Address> findAllByUser(User user) {
        return addressRepository.findAllByUserId(user.getId());
    }

    @Override
    public Address addAddressForUser(Address address, User user) {
        address.setUser(user);
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
