package com.kbe.kompsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class KompSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(KompSysApplication.class, args);

    }


}

