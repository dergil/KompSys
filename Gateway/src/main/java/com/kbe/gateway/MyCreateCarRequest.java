package com.kbe.gateway;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCreateCarRequest implements Serializable {

    @NotBlank
    private String name;
    //    @NotNull
    @Min(0)
    private Double price;
    private float milesPerGallon;
    private int cylinders;
    private int displacement;
    private int horsepower;
    private int weightInPounds;
    private float acceleration;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate year;
    private String origin;

}
