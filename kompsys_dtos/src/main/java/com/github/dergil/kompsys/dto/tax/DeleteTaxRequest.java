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
