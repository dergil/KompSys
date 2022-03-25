package com.github.dergil.kompsys.dto.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate year;
  private String origin;
}
