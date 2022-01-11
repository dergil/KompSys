package com.kbe.storage.domain.dto.tax;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EditTaxRequest {

    @NotNull
    private Long id;
    @NotBlank
    private String ipAddr;
}
