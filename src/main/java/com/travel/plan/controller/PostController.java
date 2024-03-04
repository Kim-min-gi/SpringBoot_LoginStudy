package com.travel.plan.controller;

import com.travel.plan.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

//    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> prams){
//        log.info("prams = {}" , prams);
//        return "Hello World";
//    }

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate prams){
        log.info("prams = {}" , prams.toString());
        return "Hello World";
    }


}
