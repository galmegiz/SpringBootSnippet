package com.example.demo.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component 등록하면 ErrorMvcAutoConfiguration이 작동 안 함
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    /*
    과거 톰캣 서버 xml에 에러페이지 등록하던 것과 동일한 기능
     */

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/exception/error-page/400");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/exception/error-page/500");

        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/exception/error-page/500");
        factory.addErrorPages(errorPage400, errorPage500, errorPageEx);
    }
}
