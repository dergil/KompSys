package com.github.dergil.kompsys.dto.car;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteCarRequest implements Serializable {
    long id;
}
