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

public class MemberFormControllerV3 implements ControllerV3 {


    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
