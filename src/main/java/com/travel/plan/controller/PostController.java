package com.travel.plan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/posts")
    public String get(@RequestParam Map<String, String> prams){
        //log.info();
        return "Hello World";
    }


}
