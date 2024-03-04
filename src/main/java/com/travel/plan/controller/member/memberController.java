package com.travel.plan.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class memberController {

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @PostMapping("/login/auth")
    public String loginAuth(){
        return "redirect:/";
    }

}
