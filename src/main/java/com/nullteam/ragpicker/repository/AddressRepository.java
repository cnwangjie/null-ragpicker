package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>Title: AddressRepository.java</p>
 * <p>Package: com.nullteam.ragpicker.repository</p>
 * <p>Description: 地址仓库</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findAllByUserId(Integer userid);
}
