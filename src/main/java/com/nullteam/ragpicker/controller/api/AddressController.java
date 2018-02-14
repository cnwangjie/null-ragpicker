package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // TODO: full test & debug log

    @GetMapping(value = "/user/{userId}/address")
    public ResponseEntity listUserAddress(@PathVariable Integer userId) {
        List<Address> addresses = addressService.findAllByUserId(userId);
        return ResponseEntity.ok().body(addresses);
    }

    @PostMapping(value = "/user/{userId}/address")
    public ResponseEntity addAddress(@PathVariable Integer userId,
                                     @Valid Address address) {
        address = addressService.addAddressForUserById(address, userId);
        return ResponseEntity.ok().body(address);
    }

    @GetMapping(value = "/address/{addressId}")
    public ResponseEntity getAddress(@PathVariable Integer addressId) {
        Address address = addressService.getOneById(addressId);
        if (address == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(address);
    }

    @PostMapping(value = "/address/{addressId}")
    public ResponseEntity updateAddress(@PathVariable Integer addressId,
                                        @Valid Address newAddress) {
        Address oldAddress = addressService.getOneById(addressId);
        if (oldAddress == null) return ResponseEntity.notFound().build();
        oldAddress.setTel(newAddress.getTel());
        oldAddress.setDetail(newAddress.getDetail());
        oldAddress.setLocation(newAddress.getLocation());
        newAddress = addressService.update(oldAddress);
        return ResponseEntity.ok().body(newAddress);
    }

    @PostMapping(value = "/address/{addressId}/delete")
    public ResponseEntity deleteAddress(@PathVariable Integer addressId) {
        Address address = addressService.getOneById(addressId);
        if (address == null) return ResponseEntity.notFound().build();
        addressService.delete(address);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"success\"}");
    }

}
