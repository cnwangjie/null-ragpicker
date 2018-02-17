package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Collector;

import java.util.List;

public interface CollectorService {

    Collector create(Collector collector);

    List<Collector> getAllByLocationIdEquals(Integer location);

    Collector getOneById(Integer collectorId);
}
