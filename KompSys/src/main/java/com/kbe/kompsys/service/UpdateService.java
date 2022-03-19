package com.kbe.kompsys.service;

import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.github.dergil.kompsys.dto.update.UpdateStorageResponse;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateService {
    public static final String ROUTING_KEY = "storage";
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public UpdateService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @Scheduled(fixedRate = 5000)
    private void updateCarRepositoryByMetric() {
        double counter = Metrics.counter("db_changes", "change", "car").count();
        if (counter > 10) {
            log.info("Changes are greater then 10");
            queryUpdateStorage(new UpdateStorage(counter));
            Metrics.counter("db_changes", "change", "car").increment(-counter);

            log.info("DECREMENT: " + Metrics.counter("db_changes", "change", "car").count());

        } else {
            log.info("...waiting for more changes to be made - currently: " + counter);
        }
    }

    public UpdateStorageResponse queryUpdateStorage(UpdateStorage request) {
        log.info("Sending " + request);
        UpdateStorageResponse response = (UpdateStorageResponse) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                ROUTING_KEY,
                request);
        log.info("Received " + response);
        return response;
    }
}
