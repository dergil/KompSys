package com.github.dergil.kompsys.dto.tax;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaxRequest implements Serializable {
  @NotBlank
  private String countryCodeID;
}
