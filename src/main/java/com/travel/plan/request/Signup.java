package com.travel.plan.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {

    private String email;
    private String name;
    private String password;

}
