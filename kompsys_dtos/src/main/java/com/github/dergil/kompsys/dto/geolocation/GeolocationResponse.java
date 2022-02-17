package com.github.dergil.kompsys.dto.geolocation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class GeolocationResponse implements Serializable {
    @NotBlank
    private String countryCode;
    @NotBlank
    private String region;
}
