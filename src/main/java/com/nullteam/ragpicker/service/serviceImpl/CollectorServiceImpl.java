package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.repository.CollectorRepository;
import com.nullteam.ragpicker.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectorServiceImpl implements CollectorService {

    @Autowired
    private CollectorRepository collectorRepository;

    @Override
    public Collector create(Collector collector) {
        return collectorRepository.save(collector);
    }

    @Override
    public List<Collector> getAllByLocationIdEquals(Integer location) {
        return collectorRepository.findByLocationEquals(location);
    }

    @Override
    public Collector getOneById(Integer collectorId) {
        return collectorRepository.getOne(collectorId);
    }
}
