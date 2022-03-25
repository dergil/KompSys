package com.github.dergil.kompsys.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxViewList implements Serializable {
  private List<TaxView> taxViews;

  /**
   * Gets taxViews
   *
   * @return value of taxViews
   */
  public List<TaxView> getTaxViews() {
    return taxViews;
  }

  /**
   * Sets taxViews
   *
   * @return value of taxViews
   */
  public void setTaxViews(List<TaxView> taxViews) {
    this.taxViews = taxViews;
  }
}
