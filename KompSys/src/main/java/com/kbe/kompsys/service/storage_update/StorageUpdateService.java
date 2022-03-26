package com.kbe.kompsys.service.storage_update;

import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.github.dergil.kompsys.dto.update.UpdateStorageResponse;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.kbe.kompsys.service.RabbitMqTransferService;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class StorageUpdateService {
    @Autowired
    private RabbitMqTransferService transferService;
    public final String ROUTING_KEY = "storage";

    @Autowired
    private CsvExporter csvExporter;
    @Autowired
    private SftpServiceImpl storageService;

    public void forceUpdateCarRepo() throws JSchException, SftpException, IOException {
        Metrics.counter("db_changes", "change", "car").increment(11);
        updateCarRepositoryByMetric();
    }

    @Scheduled(fixedRate = 5000)
    private void updateCarRepositoryByMetric() throws IOException, JSchException, SftpException {
        double counter = Metrics.counter("db_changes", "change", "car").count();
        if (counter > 10) {
            log.info("Changes are greater then 10");
            csvExporter.exportCarsToCSV();
            storageService.putFile("/home/spring/csv/cars.csv", "/upload/");
            UpdateStorageResponse response = queryUpdateStorage(new UpdateStorage(counter));
            log.info("Changes made in storage: " + response.changesMade);
            Metrics.counter("db_changes", "change", "car").increment(-counter);
            log.info("DECREMENT: " + Metrics.counter("db_changes", "change", "car").count());
        } else {
            log.info("...waiting for more changes to be made - currently: " + counter);
        }
    }

    private UpdateStorageResponse queryUpdateStorage(UpdateStorage request) {
        log.info("Sending " + request);
        UpdateStorageResponse response = (UpdateStorageResponse) transferService.transferRequest(request, ROUTING_KEY);
        log.info("Received " + response);
        return response;
    }
}
