package com.kbe.kompsys;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.kbe.kompsys.service.interfaces.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private StorageService storageService;

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() throws JSchException, SftpException {
        log.info("SFTP put");
        storageService.putFile("/home/spring/csv/file.txt", "/upload/");

        log.info("SFTP get");
        storageService.getFile("/upload/file.txt", "/home/spring/csv2/");
    }
}
