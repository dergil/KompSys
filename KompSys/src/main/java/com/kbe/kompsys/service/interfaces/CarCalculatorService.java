package com.kbe.kompsys.service.interfaces;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;

public interface CarCalculatorService {
    CalculateResponse queryCalculator(CalculateRequest request);
}
