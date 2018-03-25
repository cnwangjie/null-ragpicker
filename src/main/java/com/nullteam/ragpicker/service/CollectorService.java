package com.nullteam.ragpicker.service;

import com.nullteam.ragpicker.model.Collector;

import java.util.List;

/**
 * <p>Title: CollectorService.java</p>
 * <p>Package: com.nullteam.ragpicker.service</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/13/18
 * @author Robin <robinchow8991@gmail.com>
 * @author WangJie <i@i8e.net>
 */
public interface CollectorService {

    Collector create(Collector collector);

    List<Collector> getAllByLocationIdEquals(Integer location);

    Collector getOneById(Integer collectorId);
}
