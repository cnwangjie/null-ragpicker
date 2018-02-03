package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findAllByUserId(Integer userid);
}
