package com.kbe.kompsys.domain.dto.tax;

import com.kbe.kompsys.domain.model.Tax;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
public class TaxResponse {
    private Tax tax;
    private String country;
    private String region;
}
