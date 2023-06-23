package com.MVC.v2.controller;

import com.MVC.MyView;
import com.MVC.v1.ControllerV1;
import com.MVC.v2.ControllerV2;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        //회원 저장 로직

        request.setAttribute("member", new Object());
        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
