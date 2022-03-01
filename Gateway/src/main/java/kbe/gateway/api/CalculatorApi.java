package kbe.gateway.api;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import kbe.gateway.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CalculatorApi {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/calculate")
    public CalculateResponse calculate(
            @RequestParam double pricePreTax, @RequestParam double salesTax){
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPricePreTax(pricePreTax);
        calculateRequest.setSalesTax(salesTax);
        return calculatorService.getCalculateResponse(calculateRequest);
    }


}
