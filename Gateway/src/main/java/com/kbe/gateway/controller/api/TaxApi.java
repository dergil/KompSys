package com.kbe.gateway.controller.api;

import com.github.dergil.kompsys.dto.tax.*;
import com.kbe.gateway.service.RabbitMqTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/tax")
public class TaxApi {

  @Autowired
  private RabbitMqTransferService transferService;
  @Value("${tax_queue_routing_key:storage}")
  private String storage_queue_routing_key;

  @PostMapping
  public TaxView create(@RequestBody @Valid CreateTaxRequest request) {
    return (TaxView) transferService.transferRequest(request, storage_queue_routing_key);
  }

  @GetMapping
  public TaxView get(@RequestParam @Valid String countryCodeID) {
    return (TaxView) transferService.transferRequest(new ReadTaxRequest(countryCodeID), storage_queue_routing_key);
  }

  @GetMapping("/all")
  public TaxViewList getAll() {
    return (TaxViewList) transferService.transferRequest(new ReadAllTaxesRequest(), storage_queue_routing_key);

  }

  @PutMapping
  public TaxView update(@RequestBody @Valid EditTaxRequest request) {
    return (TaxView) transferService.transferRequest(request, storage_queue_routing_key);
  }

  @DeleteMapping
  public TaxView delete(@RequestBody @Valid DeleteTaxRequest request) {
    return (TaxView) transferService.transferRequest(request, storage_queue_routing_key);
  }

}
