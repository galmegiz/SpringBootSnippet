package com.MVC.v3.controller;

import com.MVC.ModelView;
import com.MVC.MyView;
import com.MVC.v2.ControllerV2;
import com.MVC.v3.ControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", new Object());
        //회원 저장 로직
        return mv;
    }
}
