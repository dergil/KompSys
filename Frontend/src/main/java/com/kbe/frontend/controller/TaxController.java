package com.kbe.frontend.controller;

import com.github.dergil.kompsys.dto.tax.*;
import com.kbe.frontend.factory.UriFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/tax")
public class TaxController {

  private final RestTemplate restTemplate = new RestTemplate();

  @GetMapping
  public String get_manage_view(Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/tax/all", null);
    List<TaxView> taxViewList = Objects.requireNonNull(restTemplate.getForObject(builder.toString(), TaxViewList.class)).getTaxViews();

    assert taxViewList != null;
    ArrayList<String> countryList = new ArrayList<>();
    for (TaxView taxView :
            taxViewList) {
      countryList.add(taxView.getCountryCodeID());
    }
    model.addAttribute("countryList", countryList);
    return "manage_tax";
  }

  @PostMapping(params = {"countryCodeID", "tax"})
  public String create(@RequestParam("countryCodeID") String countryCodeID,
                       @RequestParam("tax") double tax,
                       Model model) {

    URIBuilder builder = UriFactory.buildUri("/api/v1/tax", null);

    CreateTaxRequest createTaxRequest = new CreateTaxRequest(countryCodeID, tax);
    HttpEntity<CreateTaxRequest> r = new HttpEntity<>(createTaxRequest);
    buildingLog(r);
    ResponseEntity<TaxView> foo = restTemplate.exchange(builder.toString(), HttpMethod.POST, r, TaxView.class);

    model.addAttribute("taxViewList", foo.getBody());

    return "tax_get_all";
  }


  @GetMapping(params = {"countryCodeID"})
  public String get(@ModelAttribute @RequestParam @Valid String countryCodeID, Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/tax", null);
    TaxView taxView = restTemplate.getForObject(builder.addParameter("countryCodeID", countryCodeID.toUpperCase()).toString(), TaxView.class);

    model.addAttribute("taxViewList", taxView);
    model.addAttribute("response", "Fetched");
    return "tax_get_all";
  }

  @GetMapping("/all")
  public String getAll(Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/tax/all", null);
    List<TaxView> taxViewList = Objects.requireNonNull(restTemplate.getForObject(builder.toString(), TaxViewList.class)).getTaxViews();

    assert taxViewList != null;
    model.addAttribute("taxViewList", taxViewList);
    model.addAttribute("response", "Fetched");
    return "tax_get_all";
  }


  @PostMapping(params = {"countryCodeIDtoUpdate", "taxUpdate"})
  public String update(@RequestParam(value = "countryCodeIDtoUpdate") String countryCodeIDtoUpdate,
                       @RequestParam(value = "taxUpdate") double taxUpdate,
                       Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/tax", null);

    EditTaxRequest editTaxRequest = new EditTaxRequest(countryCodeIDtoUpdate, taxUpdate);
    HttpEntity<EditTaxRequest> r = new HttpEntity<>(editTaxRequest);
    ResponseEntity<TaxView> foo = restTemplate.exchange(builder.toString(), HttpMethod.PUT, r, TaxView.class);

    model.addAttribute("taxViewList", foo.getBody());
    model.addAttribute("response", "Updated");
    return "tax_get_all";
  }

  @PostMapping(params = {"countryCodeIDtoDelete"})
  public String delete(@RequestParam(value = "countryCodeIDtoDelete") String countryCodeIDtoDelete,
                       Model model) {
    DeleteTaxRequest deleteTaxRequest = new DeleteTaxRequest(countryCodeIDtoDelete);
    URIBuilder builder = UriFactory.buildUri("/api/v1/tax", null);
    HttpEntity<DeleteTaxRequest> r = new HttpEntity<>(deleteTaxRequest);
    log.info("Building Httprequest for Tax-Api: " + r);
    buildingLog(r);
    ResponseEntity<TaxView> foo = restTemplate.exchange(builder.toString(), HttpMethod.DELETE, r, TaxView.class);
    log.info("Object received by Tax-Api: " + foo);
    model.addAttribute("taxViewList", foo.getBody());
    model.addAttribute("response", "Deleted");
    return "tax_get_all";
  }

  private void buildingLog(Object o) {
    log.info("Building Httprequest for Tax-Api: " + o);
  }
}
