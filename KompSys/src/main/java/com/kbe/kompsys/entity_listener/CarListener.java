package com.kbe.kompsys.entity_listener;


import com.kbe.kompsys.domain.model.Car;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class CarListener {

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onPostAction(Car o) {
        Metrics.counter("db_changes", "change", "car").increment();
    }
}

