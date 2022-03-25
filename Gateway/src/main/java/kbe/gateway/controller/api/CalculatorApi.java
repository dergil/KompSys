package kbe.gateway.controller.api;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import kbe.gateway.service.RabbitMqTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CalculatorApi {

  @Value("${calculate_routing_key:calculate}")
  public String CALCULATE_QUEUE_ROUTING_KEY;

  @Autowired
  private RabbitMqTransferService transferService;

  @GetMapping("/calculate")
  public CalculateResponse calculate(
          @RequestParam double pricePreTax, @RequestParam double salesTax) {
    CalculateRequest calculateRequest = new CalculateRequest(pricePreTax, salesTax);
    return (CalculateResponse) transferService.transferRequest(calculateRequest, CALCULATE_QUEUE_ROUTING_KEY);
  }


}
