package com.example.demo.controller.auth;

import com.example.demo.domain.Member;
import com.example.demo.util.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class VanillaSessionController {

    private final SessionManager sessionManager;

    @PostMapping("/login")
    public String login(String loginForm, HttpServletResponse response){

        //로그인 로직
        Member loginMember = new Member();

        if (loginMember == null) {
            return "login/loginForm";
        }

        sessionManager.createSession(loginMember, response);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    //authorization logic
    @PostMapping("/")
    public String home(HttpServletRequest request, Model model) {
        Member member = (Member) sessionManager.getSession(request);

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        //authorization 필요한 resource response
        return "loginHome";
    }

}
