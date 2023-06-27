package com.example.demo.controller.exception;

import com.example.demo.exception.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
@RequestMapping("/exception")
@Slf4j
public class ExceptionController {

    //ExceptionHandler, 이 controller에서 발생한 exception 처리
    //controller error -> ExceptionHandlerResolver -> Controller에서 ExceptoinHandler찾기 -> handler method호출 -> 정상 로직
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }



    //@ResponseEntity 사용하지 않고 에러처리
    //ExceptionHandler의 parameter와 반환값은 controller처럼 다양한 값을 넘길 수 있다.
    //자세한 내용은 Spring Web MVC Ref 참고
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExHandler(EntityNotFoundException e) {
        log.error("[exceptionHandler] ex", e);
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ex1")
    public String ex1() {
        throw new IllegalArgumentException();
    }

    @GetMapping("/ex2")
    public String ex2() throws IllegalAccessException{
        throw new IllegalAccessException();
    }

    //@ResponseStatus가 붙은 Exception은 ResponseStatusExceptionResolver가 처리, sendError로 처리하기 때문에 에러페이지 호출 로직을 다시
    @GetMapping("/ex3")
    public String ex3() {
        throw new BadRequestException();
    }

    //ResponseStatusExceptionResolver가 처리, sendError로 처리하기 때문에 에러페이지 호출 로직을 다시
    @GetMapping("/ex4")
    public String ex4(){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.bad", new IllegalArgumentException());
    }

    //Spring 내부 exception은 defaultHandlerExceptionResolver가 500 -> 400으로 변환해준다. 이 역시 sendError로 로직 처리
    @GetMapping("/ex5")
    public String ex5(){
        throw new TypeMismatchException("Aaa");
    }

    @RequestMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400 오류");
    }

    @RequestMapping("/error-page/400")
    public String errorPage400() throws IOException {
        return "error-page/400";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500() throws IOException {
        //view render moudle 없으면 경로로 인식해서 /exception/error-page/error-page/500을 호출함
        return "error-page/500";
    }

    @GetMapping("/ex6")
    public String ex6() throws Exception {
        throw new Exception();
    }

}
