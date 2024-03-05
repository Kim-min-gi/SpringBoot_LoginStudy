package com.travel.plan.controller;

import com.travel.plan.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

        ErrorResponse errorResponse = new ErrorResponse("400","잘못된 요청 입니다.");

        for (FieldError fieldError : e.getFieldErrors()){
            errorResponse.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return errorResponse;

    }

}
