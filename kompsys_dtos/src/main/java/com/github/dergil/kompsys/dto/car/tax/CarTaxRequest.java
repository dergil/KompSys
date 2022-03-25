package com.github.dergil.kompsys.dto.car.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTaxRequest implements Serializable {
  private long id;
  private String ipAddress;
}
