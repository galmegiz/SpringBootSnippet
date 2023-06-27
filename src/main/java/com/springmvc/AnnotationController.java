package com.springmvc;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

//매개변수는 HandlerMethodArgumentResolver에서 처리
//응답은 HandlerMethodReturnValueHandler에서 처리
//일부 매개변수의 경우 ArgumentResolver가 HttpMessagerConverter 구현체 이용해 처리
//@RequestMapping, @Controller -> RequestmappingHandlerMapping에서 controller 찾음 -> RequestMappingHandlerAdapter
//애노테이션 기반이기 때문에 메소드 이름은 자유
@Controller
@Slf4j
public class AnnotationController {

    public void logging(){
        log.info("sss");
        log.trace("sss");
        log.debug("sss");
        log.error("sss");

    }

    @GetMapping({"/mebmer/1", "member/2"})
    public String sample(){
        return null;
    }

    @GetMapping({"/mebmer/{userId}/orders/{orderId}"})
    public String sample2(@PathVariable String userId, @PathVariable Long orderId){
        return null;
    }

    //Request param에 mode=debug있어야 호출됨
    @GetMapping(value = "/mebmer", params = "mode=debug")
    public String sample3(){
        return null;
    }

    //header mode=debug있으면 호출
    @GetMapping(value = "/mebmer", headers = "mode=debug")
    public String sample4(){
        return null;
    }

    //content Type에 따라 호출
    @GetMapping(value = "/mebmer", consumes = "application/json")
    public String sample5(){
        return null;
    }

    //accept 헤더에 따라 호출
    @GetMapping(value = "/mebmer", produces = "application/json")
    public String sample6(){
        return null;
    }

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("header") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie,
                          @RequestParam Map<String, Object> paramMap,
                          @RequestParam MultiValueMap<String, Object> paramMaps/*파라미터명 중복되는 경우*/) {
        return null;

    }

    //String, int, Integer같은 단순 타입은 RequestParam, ArgumentResolver에서 지정하지 않은 나머지 타입은 modelAttribute
    //요청파라미터의 이름으로 매개변수 객체의 프로퍼티를 찾는다. 이후 setter를 호출해서 파라미터의 값을 바인딩한다.
    @RequestMapping("ss")
    public String modelAttribute(@ModelAttribute Object object) {
        return null;
    }

    @RequestMapping("responsebody")
    public void requestBodySample(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        response.getWriter().write("ok");
    }

    @RequestMapping("responsebody2")
    public void requestBodySample2(InputStream inputStream, Writer responseWriter) throws IOException {

        StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        responseWriter.write("ok");
    }
    //body를 그대로 읽기, HttpMessageConverter 작동
    @RequestMapping("responsebody3")
    public HttpEntity<String> requestBodySample3(HttpEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();
        return new HttpEntity<>("ok");
    }

    //HttpEntity 상속
    @RequestMapping("responsebody3")
    public ResponseEntity<String> requestBodySample3(RequestEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    //HttpEntity나 @RequestBody를 사용하면 Http메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원한는 문자나 객체 등으로 반환해준다.
    //Http메시지 컨버터는 문자 뿐 아니라 JSON도 객체로 변환해준다.

    @ResponseBody
    @RequestMapping("responsebody4")
    public ResponseEntity<String> requestBodySample3(@RequestBody String httpEntity) throws IOException {

        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    /*
    @RequestBodt, HttpEntity, @ResponseBody, HttpEntity를 사용하면 Http메시지 컨버터 적용


    @ResponseBody를 사용하면 Http의 body에 문자내용을 직접 반환
    viewResolver대신에 HttpMessageConverter가 동작
    기본 문자처리 : StringHttpMessageConverter
    기본 객체처리 : MappingJackson2HttpMessageConverter
    byte처리 등등 기타 여러 HttpMessageConver가 기본적으로 등록되어 있음
     */

    /*
    스프링 부트 기본 메시지 컨버터
    1. ByteArrayHttpMessageConverter
        클래스타입 : byte[], 미디어타입 *\/*, 쓰가 미디어 타입 application/octet-stream
    2. StringHttpMessageConverter
        클래스타입 String, 미디터타입 *\/*, 쓰기 미디어 타입 text/plain
    3. MappingJackson2HttpMessageConverter
        클래스타입 : 객체 또는 HashMap, 미디어 타입 application/json


     각 컨버터의 canRead(), canWrite()를 넘어가면 다음 컨버터 호출


     @RequestBody -> @RequestBody처리 argument resolver찾음 -> argument Resolver(HttpEntityMethodProcessor -> HttpEntity, @RequestBody -> RequestResponseBodyMethodProcessor)가 HttpMessageConverter찾음
     */



}



