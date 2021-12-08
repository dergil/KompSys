package com.kbe.kompsys.domain.dto;

import com.kbe.kompsys.domain.model.Tax;
import lombok.Data;

import java.util.Date;

@Data
public class TaxResponse {
    private Tax tax;
    private String country;
    private String region;
}
