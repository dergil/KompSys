package com.kompsys.calculator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CalculateRequest implements Serializable {
    double price;
    double salesTax;
}
