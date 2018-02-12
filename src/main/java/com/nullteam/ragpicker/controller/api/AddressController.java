package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.repository.AddressRepository;
import com.nullteam.ragpicker.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/user/{id}/address")
    @PreAuthorize("hasRole('ROLE_USER') and #id == principal.id")
    public ResponseEntity listUserAddress(@PathVariable Integer id) {
        List<Address> addresses = addressService.FindAllByUserId(id);
        return new ResponseEntity(addresses, HttpStatus.OK);
    }

    @GetMapping(value = "/address/{id}")
    public ResponseEntity getAddressDetail(@PathVariable Integer id) {
        Address address = addressService.Read(id);
        return new ResponseEntity(address, HttpStatus.OK);
    }

    @PostMapping(value = "/address/{id}")
    public ResponseEntity updateAddressDetail(@PathVariable Integer id,
                                              @ModelAttribute Address address) {
        addressService.Update(address);
        return new ResponseEntity(addressService.Read(id), HttpStatus.OK);
    }

}
