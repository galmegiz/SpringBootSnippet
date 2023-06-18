package com.example.demo.controller.auth;

import com.example.demo.annotation.Login;
import com.example.demo.constant.SessionConst;
import com.example.demo.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("argument-resolver")
public class ArgumentResolverController {
    @GetMapping("/")
    public String home(@Login Member loginMember, Model model){

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
