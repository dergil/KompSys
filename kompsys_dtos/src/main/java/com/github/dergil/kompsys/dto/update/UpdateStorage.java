package com.github.dergil.kompsys.dto.update;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateStorage implements Serializable {
  public double changesMade;
}
