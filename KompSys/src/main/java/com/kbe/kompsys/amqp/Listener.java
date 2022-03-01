//package com.kbe.kompsys.amqp;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//public class Listener {
//
//    private boolean failed;
//
//    @RabbitListener(id = "foo", queues = "#{queue1.name}")
//    public String foo(String foo) {
//        return foo.toUpperCase();
//    }
//
//    @RabbitListener(id = "bar", queues = "#{queue2.name}")
//    public void foo(@Payload String foo, @SuppressWarnings("unused") @Header("amqp_receivedRoutingKey") String rk) {
//        if (!failed && foo.equals("ex")) {
//            failed = true;
//            throw new IllegalArgumentException(foo);
//        }
//        failed = false;
//    }
//
//}
