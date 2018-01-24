package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
