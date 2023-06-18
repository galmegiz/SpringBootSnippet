package com.example.demo.advice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//대상 지정하지 않으면 모든 컨트롤러에 적용
@Slf4j
//@ControllerAdvice(annotations = @RestController.class)
//@ControllerAdvice("org.example.controllers")

@RestControllerAdvice
public class advice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> entityNotFoundExHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
