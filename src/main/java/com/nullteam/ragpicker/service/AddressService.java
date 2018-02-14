package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Address;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AddressService {

    @PreAuthorize("hasRole('ROLE_USER') and #userId == principal.id")
    List<Address> findAllByUserId(Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') and #userId == principal.id")
    Address addAddressForUserById(Address address, Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') and #address.user?.id == principal.id")
    Address update(Address address);

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostAuthorize("returnObject.user.id == principal.id")
    Address getOneById(Integer id);

    @PreAuthorize("hasRole('ROLE_USER') and #address.user.id == principal.id")
    void delete(Address address);

}
