package com.kbe.kompsys.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaxRequest {
    @NotNull
    private String id;
    @NotBlank
    private String ipAddr;
}
