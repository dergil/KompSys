package com.github.dergil.kompsys.dto.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
  private LocalDate year;
  private String origin;
}
