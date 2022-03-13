package com.kbe.kompsys.entityListener;


import com.kbe.kompsys.domain.model.Car;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Slf4j
@Component
public class CarListener {
    MeterRegistry registry = new SimpleMeterRegistry();

    //    final Counter counter = Counter
//            .builder("my.counter")
//            .description("counts db changes")
//            .tag("environment", "test")
//            .register(oneSimpleMeter);
//    @PostConstruct
//    public void init() {
//        Metrics.addRegistry(new SimpleMeterRegistry());
//    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onPostAction(Car o) {

        Metrics.counter("db.changes", "change", "car").increment();
        log.info(String.valueOf(registry.counter("db.changes", "change", "car").count()));
    }

}

