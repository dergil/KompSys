package kbe.gateway.api;

import com.github.dergil.kompsys.dto.tax.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

@Slf4j
@RestController
@RequestMapping("/tax")
public class TaxApi {


  private final RabbitTemplate rabbitTemplate;
  private final DirectExchange directExchange;
  @Value("${tax_queue_routing_key:tax}")
  private String tax_queue_routing_key;

  public TaxApi(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.directExchange = directExchange;
  }

  @PostMapping
  public TaxView create(@RequestBody @Valid CreateTaxRequest request) {
    return transferRequest(request);
  }

  @GetMapping
  public TaxView get(@RequestParam @Valid String countryCodeID) {
    return transferRequest(new ReadTaxRequest(countryCodeID));
  }

  @GetMapping("/all")
  public TaxViewList getAll() {
    return transferRequest(new ReadAllTaxesRequest());
  }

  @PutMapping
  public TaxView update(@RequestBody @Valid EditTaxRequest request) {
    return transferRequest(request);
  }

  @DeleteMapping
  public TaxView delete(@RequestBody @Valid DeleteTaxRequest request) {
    return transferRequest(request);
  }

  @Nullable
  private TaxView transferRequest(java.io.Serializable request) {
    return (TaxView) sendRequestAndReceiveResponseObject(request);
  }

  @Nullable
  private TaxViewList transferRequest(ReadAllTaxesRequest request) {
    return (TaxViewList) sendRequestAndReceiveResponseObject(request);
  }

  private Serializable sendRequestAndReceiveResponseObject(java.io.Serializable request) {
    log.info("Sending " + request.toString());
    return (Serializable) rabbitTemplate.convertSendAndReceive(
            directExchange.getName(),
            tax_queue_routing_key,
            request
    );
  }
}
