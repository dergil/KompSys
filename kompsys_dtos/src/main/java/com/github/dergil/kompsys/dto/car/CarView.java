package com.github.dergil.kompsys.dto.car;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CarView implements Serializable {

    private long id;

    private String name;
    private int price;
    private int milesPerGallon;
    private int cylinders;
    private int displacement;
    private int horsepower;
    private int weightInPounds;
    private int acceleration;
    private String year;
    private String origin;
}
