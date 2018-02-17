package com.nullteam.ragpicker.repository;

import com.nullteam.ragpicker.model.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectorRepository extends JpaRepository<Collector, Integer> {
    List<Collector> findByLocationEquals(Integer location);
}
