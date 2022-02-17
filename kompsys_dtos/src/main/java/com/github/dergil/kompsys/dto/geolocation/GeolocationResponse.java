package com.github.dergil.kompsys.dto.geolocation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GeolocationResponse {
    @NotBlank
    private String countryCode;
    @NotBlank
    private String region;
}
