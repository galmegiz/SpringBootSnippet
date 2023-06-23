package com.MVC.v5;

import com.MVC.ModelView;
import com.MVC.MyView;
import com.MVC.v3.controller.MemberFormControllerV3;
import com.MVC.v3.controller.MemberSaveControllerV3;
import com.MVC.v4.controller.MemberFormControllerV4;
import com.MVC.v5.adapter.ControllerV3HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
핸들러매핑 : org.springframework.web.servlet.HandlerMapping
핸들러 어댑터 : org.springframework.web.servlet.handlerAdapter
뷰 리졸버 : org.springframework.web.servlet.ViewResolver
뷰 : org.springframework.servlet.View
 */
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();

        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/member/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/member/save", new MemberSaveControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/member/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/member/new-form", new MemberFormControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getView();
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/view" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {

        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)){
                return adapter;
            }
        }

        throw new IllegalArgumentException("no handler adapter" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Object controller = handlerMappingMap.get(requestURI);
        return controller;
    }
}
