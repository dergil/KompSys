package com.kbe.kompsys.domain.dto.tax;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaxRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String ipAddr;
}
