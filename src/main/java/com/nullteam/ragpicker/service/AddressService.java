package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.model.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AddressService {

    @PreAuthorize("hasRole('ROLE_USER') and #user.id == principal.id")
    List<Address> findAllByUser(User user);

    @PreAuthorize("hasRole('ROLE_USER') and #user.id == principal.id")
    Address addAddressForUser(Address address, User user);

    @PreAuthorize("hasRole('ROLE_USER') and #address.user?.id == principal.id")
    Address update(Address address);

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostAuthorize("returnObject.user.id == principal.id")
    Address getOneById(Integer id);

    @PreAuthorize("hasRole('ROLE_USER') and #address.user.id == principal.id")
    void delete(Address address);

}
