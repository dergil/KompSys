package com.github.dergil.kompsys.dto.update;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateStorage implements Serializable {
  @Min(0)
  private double changesMade;
}
