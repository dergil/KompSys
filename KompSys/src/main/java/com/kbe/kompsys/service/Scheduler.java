package com.kbe.kompsys.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private StorageServiceImpl storageService;

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() throws JSchException, SftpException {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 10000);
//        relative path needed remotely
        storageService.transferFile("/home/archdoc/temp7/file.txt", "./");
    }

}
