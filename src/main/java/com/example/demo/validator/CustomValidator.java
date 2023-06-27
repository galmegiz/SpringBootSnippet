package com.example.demo.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class CustomValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("notinhg dodod");
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("notinhg dodod");
        System.out.println("do nothing");
    }
}
