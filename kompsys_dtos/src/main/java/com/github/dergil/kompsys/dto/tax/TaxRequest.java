package com.github.dergil.kompsys.dto.tax;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TaxRequest implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String ipAddr;
}
