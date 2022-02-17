//package com.kbe.kompsys.amqp;
//
//import com.github.dergil.kompsys.dto.exception.NotFoundException;
//import com.github.dergil.kompsys.dto.temp.Person;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PersonListener {
//
//    @RabbitListener(queues = "spring-boot", returnExceptions = "true")
//    public Person receive(Person person){
//        System.out.println("Received <" + person.toString() + ">");
//        return person;
////        throw new NotFoundException(Person.class, person.getName());
//    }
//}
//
