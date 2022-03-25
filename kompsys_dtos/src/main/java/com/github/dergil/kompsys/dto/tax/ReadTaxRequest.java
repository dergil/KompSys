package com.github.dergil.kompsys.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadTaxRequest implements Serializable {

  private String countryCodeID;

  /**
   * Gets countryCodeID
   *
   * @return value of countryCodeID
   */
  public String getCountryCodeID() {
    return countryCodeID;
  }

  /**
   * Sets countryCodeID
   *
   * @return value of countryCodeID
   */
  public void setCountryCodeID(String countryCodeID) {
    this.countryCodeID = countryCodeID;
  }
}
