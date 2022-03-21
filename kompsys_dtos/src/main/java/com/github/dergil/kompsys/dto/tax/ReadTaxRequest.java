package com.github.dergil.kompsys.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReadTaxRequest implements Serializable {

  private String countryCodeID;
}
