package com.travel.plan.exception;


import lombok.Getter;

/*
*  400
* */
@Getter
public class InvalidRequest extends PlanException{

    private static final String MESSAGE = "잘못된 요청입니다.";

//    private String fieldName;
//    private String message;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName , String message) {
        super(MESSAGE);
        addValidation(fieldName,message);
//        this.fieldName = fieldName;
//        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
