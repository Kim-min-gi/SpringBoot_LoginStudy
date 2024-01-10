package com.travel.plan.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class memberController {

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

}
