package com.travel.plan.controller;

import com.travel.plan.request.PostCreate;
import com.travel.plan.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> prams){
//        log.info("prams = {}" , prams);
//        return "Hello World";
//    }

    @PostMapping("/posts")
    public Map<String ,String> post(@RequestBody @Valid PostCreate request) throws Exception {
        //데이터를 검증하는 이유
        //1. client 개발자가 깜빡할 수 있다.
        //2. client 버그로 값이 누락될 수 있다.
        //3. 외부에 침입자가 값을 임의로 조작하여 보낼 수 있다.
        //4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        //5. 서버 개발자의 편안함을 위해서

        postService.write(request);
        return Map.of();
    }


}
