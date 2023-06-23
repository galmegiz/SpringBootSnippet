package com.springmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//BeanNameUrlHandlerMapping : 스프링의 빈 이름으로 핸들러를 찾는다.
@Component("/springmvc/old-controller") //spring bean의 이름을 url로 지정
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
        스프링부트는 InternalResourceViewResolver라는 뷰 리졸버를 자동등록함.
        이때 아래 설정정보를 사용해 등록한디
        application.properites에
        spring.mvc.view.prefix=/WEB-INF/views/
        spring.mvc.view.suffix=.jsp 설정

        viewResolver에는
        BeanNameViewResolver
        InternalResourceViewResolver가 있음
         */


        return new ModelAndView("new-form");
    }
}
