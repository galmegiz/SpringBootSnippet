package com.example.demo.controller.converter;

import com.example.demo.dto.IpPort;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/converter")
public class TypeConverterController {

    //spring은 string -> int로 변환
    @GetMapping("/ex1")
    public String ex1(@RequestParam Integer data) {
        return "hello";
    }

    //converter 작동
    //custom converter를 추가하면 기본 converter보다 우선순위가 높다
    //@RequestParam은 @ReqeustParam을 처리하는 ArgumentResolver인 RequestParamMethodArgumentResolver에서
    //ConversionService를 이용한다.
    @GetMapping("/ex2")
    public String ex2(@RequestParam IpPort ipPort) {
        System.out.println("ipPort = " + ipPort);
        return "ok";
    }

    //타임리프에서 ${{..}}를 사용하면 컨버팅한 결과를 받을 수 있음
    @GetMapping("/ex3")
    public String converterView(Model model) {
        model.addAttribute("ipPort", new IpPort("1.1.1.1", 2000));
        return "view";
    }

    //@NumberFormat : NumberFormatAnnotationFormatterFactory
    //@DataTimeFormat : Jsr310DatTimeFormatAnnotationFormatterFactory
    //별도 formatter 구현하지 않아도 기본 anootation기반 기본 formatter있음
    @Data
    static class Form {
        @NumberFormat(pattern = "###,###")
        private Integer number;

        @DateTimeFormat(pattern = "yyyy=MM-dd HH:mm:ss")
        private LocalDateTime dateTime;
    }
}
