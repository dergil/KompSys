package kbe.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRemoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AmqpRemoteException.class)
    public ResponseEntity<List<String>> handleAmqpRemoteException(AmqpRemoteException ex){
        String message = ex.getMessage();
        log.error("AmqpRemoteException {}", message);
        if (message.contains("NotFoundException"))
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(List.of(ex.getMessage()));
        else
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(ex.getMessage()));
    }
}
