package com.kbe.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
@Slf4j
public class RabbitMqTransferService {

  private final RabbitTemplate rabbitTemplate;
  private final DirectExchange directExchange;

  @Autowired
  public RabbitMqTransferService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.directExchange = directExchange;
  }

  @Nullable
  public Serializable transferRequest(java.io.Serializable request, String routingKey) {
    log.info("Sending " + request.toString() + " - Routingkey is " + routingKey);

    return (Serializable) rabbitTemplate.convertSendAndReceive(
            directExchange.getName(),
            routingKey,
            request
    );
  }
}
