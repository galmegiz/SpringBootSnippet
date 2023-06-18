package com.example.demo.controller.auth;

import com.example.demo.constant.SessionConst;
import com.example.demo.domain.Member;
import com.example.demo.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/http-session")
@AllArgsConstructor
@Slf4j
public class HttpSessionController {

    private final TestService testService;

    @GetMapping("/login")
    public String login(HttpServletRequest request){

        //로그인 로직 생략

        //세션이 있으면 세션 반환 없으면 신규 세션 생성
        //getSession은 default는 true, false일 경우 세션이 없으면 null 반환
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        //server.servlet.session.timeout=60 -> global session timeout 설정
        //LastAccessedTime 이후로 timeout지나면 session 삭제
        session.setMaxInactiveInterval(500);
        session.setAttribute(SessionConst.LOGIN_MEMBER, "로그인 멤버 정보");

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }



    //session annotation 이용
    @GetMapping("/login-annotation")
    public String loginv2(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        //server.servlet.session.tracking-mode=cookie를 설정하지 않으면 최초 session 생성 시 url에 jSessionId포함해서 전송
        return "loginHome";
    }

    //session에 저장된 값들
    @GetMapping("/session-info")
    public void sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
          return;
        }

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}", name, session.getAttribute(name)));

        log.info("sessionId = {}", session.getId());
        log.info("getMaxInactiveInterval = {}", session.getMaxInactiveInterval());
        log.info("creationTime = {}", session.getCreationTime());
        log.info("lastAccessedTime = {}", session.getLastAccessedTime());
        log.info("isNew = {}", session.isNew());

    }

}
