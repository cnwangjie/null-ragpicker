package com.nullteam.ragpicker.controller.api;

import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.service.AddressService;
import com.nullteam.ragpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>Title: AddressController.java</p>
 * <p>Package: com.nullteam.ragpicker.controller.api</p>
 * <p>Description: 地址相关接口控制器</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/03/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    private final UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    // TODO: full test & debug log

    @GetMapping(value = "/user/{userId}/address")
    public ResponseEntity listUserAddress(@PathVariable Integer userId) {
        User user = userService.getOneById(userId);
        if (user == null) return ResponseEntity.notFound().build();
        List<Address> addresses = addressService.findAllByUser(user);
        return ResponseEntity.ok().body(addresses);
    }

    @PostMapping(value = "/user/{userId}/address")
    public ResponseEntity addAddress(@PathVariable Integer userId,
                                     @Valid Address address) {
        User user = userService.getOneById(userId);
        if (user == null) return ResponseEntity.notFound().build();
        address = addressService.addAddressForUser(address, user);
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
