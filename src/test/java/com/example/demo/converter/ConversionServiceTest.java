package com.example.demo.converter;

import com.example.demo.dto.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIpPortConverter());

        //converter가 적용할 만한 convert를 찾아서 자동 실행
        IpPort result = conversionService.convert("129.0.0.1:3000", IpPort.class);
        System.out.println("result = " + result);
    }
}
