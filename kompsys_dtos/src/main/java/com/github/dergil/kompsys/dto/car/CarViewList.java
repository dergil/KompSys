package com.github.dergil.kompsys.dto.car;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class CarViewList implements Serializable {
    private List<CarView> carViews;
}
