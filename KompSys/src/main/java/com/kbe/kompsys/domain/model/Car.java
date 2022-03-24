package com.kbe.kompsys.domain.model;

import com.kbe.kompsys.entity_listener.CarListener;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EntityListeners(value = CarListener.class)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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
    private String hash;
}



