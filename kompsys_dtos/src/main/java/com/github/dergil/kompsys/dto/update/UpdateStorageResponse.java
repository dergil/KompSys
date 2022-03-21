package com.github.dergil.kompsys.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateStorageResponse implements Serializable {
  @NotNull
  public double changesMade;

}
