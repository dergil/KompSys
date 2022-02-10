package com.kbe.kompsys.configuration;

import com.kbe.kompsys.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    TODO: RabbitMQ exception handling

//    @ExceptionHandler(ListenerExecutionFailedException.class)
//    public void handleListenerExecutionFailedException(ListenerExecutionFailedException ex) {
//        log.error("ListenerExecutionFailedException {}\n", ex.getMessage());
//    }

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException {}\n", ex.getMessage());
//        throw new ListenerExecutionFailedException(ex.getMessage());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiCallError<T> {

        private String message;
        private List<T> details;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RabbitError<T> {

        private String returnCode;
        private List<T> exception;

    }
}
