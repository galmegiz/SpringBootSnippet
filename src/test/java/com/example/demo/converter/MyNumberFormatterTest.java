package com.example.demo.converter;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        //converter도 등록 가능
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addFormatter(new MyNumberFormatter());

        String result = conversionService.convert(1000, String.class);
        System.out.println("result = " + result);
    }

    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("1,000", Locale.KOREA);
        System.out.println("result = " + result);
    }

    @Test
    void print() {
        String result = formatter.print(1000, Locale.KOREA);
        System.out.println(result);
    }

}