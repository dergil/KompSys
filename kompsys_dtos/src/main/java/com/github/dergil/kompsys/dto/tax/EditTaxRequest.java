package com.github.dergil.kompsys.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class EditTaxRequest implements Serializable {
  @NotBlank
  private String countryCodeID;
  @NotNull
  private double tax;
}
