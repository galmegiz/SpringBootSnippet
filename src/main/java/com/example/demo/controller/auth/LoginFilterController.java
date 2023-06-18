package com.example.demo.controller.auth;

import com.example.demo.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login-filter")
public class LoginFilterController {

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectUrl){

        //로그인 로직 생략

        //세션이 있으면 세션 반환 없으면 신규 세션 생성
        //getSession은 default는 true, false일 경우 세션이 없으면 null 반환
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        //server.servlet.session.timeout=60 -> global session timeout 설정
        //LastAccessedTime 이후로 timeout지나면 session 삭제
        session.setMaxInactiveInterval(500);
        session.setAttribute(SessionConst.LOGIN_MEMBER, "로그인 멤버 정보");

        //로그인 성공 시 동적으로 페이지 변경
        return "redirect:" + redirectUrl;
    }
}
