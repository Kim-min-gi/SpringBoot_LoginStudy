package com.travel.plan.controller;

import com.travel.plan.config.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    @GetMapping("/")
    public String mainPage(){
            return "메인 페이지 입니다.";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "사용자 페이지 입니다.";
    }

    @GetMapping("/admin")
    public String admin(){
        return "관리자 페이지 입니다.";
    }

}
