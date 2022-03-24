package com.github.dergil.kompsys.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditTaxRequest implements Serializable {
  @NotBlank
  private String countryCodeID;
  @NotNull
  private double tax;

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

  /**
   * Gets tax
   *
   * @return value of tax
   */
  public double getTax() {
    return tax;
  }

  /**
   * Sets tax
   *
   * @return value of tax
   */
  public void setTax(double tax) {
    this.tax = tax;
  }
}
