package com.example.demo.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {


            try {
                if (ex instanceof IllegalArgumentException) {
                    log.info("IllegalArgumentException resolver to 400");
                    String acceptHeader = request.getHeader("accept");
                    //response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                    //sendError하면 다시 error흐름으로 바뀐다. 따라서 응답이 ServletContainer까지 올라갔다가 다시 error 호출 로직으로 바뀐다.
                    //실제로 ResponseStatusExceptionResolver에서는 sendError를 하기 때문에 다시 /error를 호출한다.
                    response.setStatus(400);
                    return new ModelAndView("error/500");

                } else if(ex instanceof IllegalAccessException){
                    String acceptHeader = request.getHeader("accept");
                    //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
                    response.setStatus(500);

                    if(MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)){
                        log.info("json");

                        Map<String, Object> errorResult = new HashMap<>();
                        errorResult.put("ex", ex.getClass());
                        errorResult.put("message", ex.getMessage());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setCharacterEncoding("UTF-8");

                        ObjectMapper mapper = new ObjectMapper();
                        response.getWriter().write(mapper.writeValueAsString(errorResult));
                        return new ModelAndView();
                    } else{
                        log.info("html");
                        return new ModelAndView("error/500");
                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return null;
    }

}
