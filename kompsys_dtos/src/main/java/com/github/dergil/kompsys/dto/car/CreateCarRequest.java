package com.github.dergil.kompsys.dto.car;

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
public class CreateCarRequest implements Serializable {

  @NotBlank
  private String name;
  @Min(0)
  private Double price;
  @Min(0)
  private float milesPerGallon;
  @Min(0)
  private int cylinders;
  @Min(0)
  private int displacement;
  @Min(0)
  private int horsepower;
  @Min(0)
  private int weightInPounds;
  @Min(0)
  private float acceleration;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate year;
  @NotBlank
  private String origin;

}
