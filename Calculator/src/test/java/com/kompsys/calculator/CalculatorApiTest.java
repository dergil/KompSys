package com.kompsys.calculator;

import com.kompsys.calculator.api.CalculatorApi;
import com.kompsys.calculator.domain.dto.CalculateRequest;
import com.kompsys.calculator.domain.dto.CalculateResponse;
import com.kompsys.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CalculatorApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService service;

    @Test
    void calculateShouldReturnResponseFromService() throws Exception {
        CalculateResponse mockedResponse = generateCalculateResponse(10, 11, 12, 13);
        when(service.calculate(generateCalculateRequest(90000, 19))).thenReturn(mockedResponse);
        this.mockMvc.perform(get("/calculate?price=90000&salesTax=19")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceTotal").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(11.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salesTax").value(12.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.taxAmount").value(13.0));
    }

    private CalculateRequest generateCalculateRequest(double price, double salesTax){
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPrice(price);
        calculateRequest.setSalesTax(salesTax);
        return calculateRequest;
    }

    private CalculateResponse generateCalculateResponse(double priceTotal, double price, double salesTax, double taxAmount){
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setPriceTotal(priceTotal);
        calculateResponse.setPrice(price);
        calculateResponse.setSalesTax(salesTax);
        calculateResponse.setTaxAmount(taxAmount);
        return calculateResponse;
    }
}
