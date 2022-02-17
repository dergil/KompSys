package com.github.dergil.kompsys.dto.car;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarTaxRequest implements Serializable {
    private long id;
    private String ipAddress;
}
