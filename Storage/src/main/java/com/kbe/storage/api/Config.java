package com.kbe.storage.api;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Value("${storage_routingkey:storage}")
  private String ROUTING_KEY;

  @Value("${main_service_exchange_name:kompsys}")
  private String main_service_exchange_name;

  @Bean
  public Receiver listener() {
    return new Receiver();
  }

//  @Bean
//  public ConnectionFactory connectionFactory() {
//    return new CachingConnectionFactory("localhost");
//  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(main_service_exchange_name);
  }

  @Bean
  public Queue queue() {
    return new Queue("storage");
  }

  @Bean
  public Binding binding(DirectExchange directExchange, Queue queue) {
    return BindingBuilder.bind(queue)
            .to(directExchange)
            .with(ROUTING_KEY);
  }

//  @Bean
//  public RabbitAdmin admin(ConnectionFactory cf) {
//    return new RabbitAdmin(cf);
//  }
//
//  @Bean
//  public RabbitTemplate template(ConnectionFactory cf) {
//    return new RabbitTemplate(cf);
//  }
//
//  @Bean
//  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf) {
//    SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
//    containerFactory.setConnectionFactory(cf);
//    return containerFactory;
//  }

}
