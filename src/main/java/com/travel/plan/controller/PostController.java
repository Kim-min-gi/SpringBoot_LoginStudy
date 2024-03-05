package com.travel.plan.controller;

import com.travel.plan.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    public Map<String ,String> post(@RequestBody @Valid PostCreate prams, BindingResult result) throws Exception {
        //데이터를 검증하는 이유
        //1. client 개발자가 깜빡할 수 있다.
        //2. client 버그로 값이 누락될 수 있다.
        //3. 외부에 침입자가 값을 임의로 조작하여 보낼 수 있다.
        //4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        //5. 서버 개발자의 편안함을 위해서

//        if (title == null || title.equals("")){
//            // 노가다형 체크
//            // 무언가 3번 이상 반복작업을 할 때 내가 뭔가 잘못하고 있는지 의심한다.
//            // 누락가능성
//            // 생각보다 검증할게 많다.
//            // 개발자스럽지 않다.
//            throw new Exception("타이틀이 없어요");
//        }

        if (result.hasErrors()){
            List<FieldError> fieldErrorList = result.getFieldErrors();
            FieldError firstFiledError = fieldErrorList.get(0);
            String fieldName = firstFiledError.getField(); //title
            String errorMessage = firstFiledError.getDefaultMessage(); //error message

            Map<String,String> error = new HashMap<>();
            error.put(fieldName,errorMessage);

            return error;

        }

//        log.info("prams = {}" , prams.toString());
        return Map.of();
    }


}
