package com.example.demo.controller.auth;

import com.example.demo.annotation.Login;
import com.example.demo.constant.SessionConst;
import com.example.demo.domain.Member;
import com.example.demo.dto.IpPort;
import com.example.demo.validator.CustomValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("argument-resolver")
public class ArgumentResolverController {

    private final CustomValidator customValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(customValidator);
    }

    @GetMapping("/")
    public String home(@Login Member loginMember, Model model, @RequestBody IpPort ipPort){

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @ResponseBody
    @GetMapping("/2")
    public ResponseEntity<Member> home2(@Valid Member loginMember, BindingResult bindingResult){


        return ResponseEntity.ok(loginMember);
    }

    @ResponseBody
    @GetMapping("/3")
    public ResponseEntity<Member> home3(@Valid @RequestBody Member loginMember, BindingResult bindingResult){


        return ResponseEntity.ok(loginMember);
    }
}
