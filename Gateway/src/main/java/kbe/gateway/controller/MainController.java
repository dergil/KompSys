package kbe.gateway.controller;

import com.github.dergil.kompsys.dto.tax.CreateTaxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

  @GetMapping("")
  public String showHomePage(Model model) {
    CreateTaxRequest tr = new CreateTaxRequest();

    model.addAttribute("taxRequest", tr);

    return "index";
  }

}
