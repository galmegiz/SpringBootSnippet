package com.example.demo.converter;

import com.example.demo.dto.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

//스프링은 Converter, ConverterFactory, GenericConverter, ConditionalGenericConverter 제공
//스프링은 문자, 숫자, 불린, enum등 일반적인 컨버터를 제공중 'Converter', 'ConverterFactory', 'GenericConverter' 구현체 참조
@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {

    @Override
    public IpPort convert(String source) {
        log.info("converter source = {}", source);
        String[] split = source.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new IpPort(ip, port);
    }
}
