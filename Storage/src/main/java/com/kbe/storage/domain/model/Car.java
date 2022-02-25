package com.kbe.storage.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private int price;
  private double milesPerGallon;
  private int cylinders;
  private int displacement;
  private int horsepower;
  private int weightInPounds;
  private double acceleration;
  private LocalDate year;
  private String origin;

}
