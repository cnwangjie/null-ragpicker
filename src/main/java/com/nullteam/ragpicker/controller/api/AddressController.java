package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(value = "/user/{id}/address", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER') and #id == principal.id")
    public ResponseEntity listUserAddress(@PathVariable Integer id) {
        List addresses = addressRepository.findAllByUserId(id);
        return new ResponseEntity(addresses, HttpStatus.OK);
    }
}
