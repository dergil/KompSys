package kbe.gateway.Controller;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import kbe.gateway.service.RabbitMqTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculateController {
  @Autowired
  private RabbitMqTransferService transferService;

  @GetMapping("/calculate")
  public String showCalculateForm(Model model) {
    model.addAttribute("calculateRequest", new CalculateRequest());
    return "calculate_form";
  }

  @PostMapping("/calculate")
  public String calculateRequest(@RequestParam double pricePreTax, @RequestParam double salesTax, Model model) {
    CalculateRequest calculateRequest = new CalculateRequest();
    calculateRequest.setPricePreTax(pricePreTax);
    calculateRequest.setSalesTax(salesTax);
    CalculateResponse calculateResponse = transferService.transferRequest(calculateRequest);

    model.addAttribute("calculateResponse", calculateResponse);
    return "calculate";
  }
}
