package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.repository.CollectorRepository;
import com.nullteam.ragpicker.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectorServiceImpl implements CollectorService {

    @Autowired
    private CollectorRepository collectorRepository;

    @Override
    public Collector Save(Collector collector) {
        return collectorRepository.save(collector);
    }
}
