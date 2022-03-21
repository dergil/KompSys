package com.github.dergil.kompsys.dto.tax;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class DeleteTaxRequest implements Serializable {
  @NotBlank
  private String countryCodeID;


}
