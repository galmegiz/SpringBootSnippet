package com.MVC.v4.controller;

import com.MVC.v4.ControllerV4;


import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));


        //회원 저장 로직

        model.put("member", new Object());
        return "save-result";
    }


}
