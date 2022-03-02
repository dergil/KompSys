package com.github.dergil.kompsys.dto.tax;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateTaxRequest implements Serializable {

  @NotBlank
  private String countryCodeID;
  @NotNull
  private double tax;
}


