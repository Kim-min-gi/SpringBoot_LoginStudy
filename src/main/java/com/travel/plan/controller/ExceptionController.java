package com.travel.plan.controller;

import com.travel.plan.exception.InvalidRequest;
import com.travel.plan.exception.PlanException;
import com.travel.plan.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()){
            errorResponse.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return errorResponse;

    }

   // @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlanException.class)
    public ResponseEntity<ErrorResponse>  PlanException(PlanException e){
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        //응답 json validation -> title : 제목에 바보를 포함할 수 없습니다.
//        if (e instanceof InvalidRequest){
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message =  invalidRequest.getMessage();
//            body.addValidation(fieldName,message);
//        }


        return ResponseEntity.status(statusCode)
                 .body(body);

    }


}
