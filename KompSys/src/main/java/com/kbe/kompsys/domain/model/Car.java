package com.kbe.kompsys.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash("Car")
public class Car  {
//    @GeneratedValue(strategy = GenerationType.AUTO)


    @Id
    private String id;

    private String name;
    private int price;
    private int milesPerGallon;
    private int cylinders;
    private int displacement;
    private int horsepower;
    private int weightInPounds;
    private int acceleration;
    private LocalDate year;
    private String origin;

}
