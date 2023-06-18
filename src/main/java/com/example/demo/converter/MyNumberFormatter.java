package com.example.demo.converter;

import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

//sprinboot는 DefaultFormattingConversionService를 상속받은 webConversionService
//FormattingConversionService가 포맷터를 지원하는 컨버전 서비스이다.
//formatter는 converter의 일종
//스프링에서는 다양한 포맷터 지원
public class MyNumberFormatter implements Formatter<Number> {
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        // "1,000" -> 1000
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        return NumberFormat.getInstance(locale).format(object);

    }
}
