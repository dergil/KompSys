package com.github.dergil.kompsys.dto.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class EditCarRequest implements Serializable {

    long id;
    @NotBlank
    private String name;
    private Double price;
    private float milesPerGallon;
    private int cylinders;
    private int displacement;
    private int horsepower;
    private int weightInPounds;
    private float acceleration;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate year;
    private String origin;
}
