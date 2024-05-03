package com.travel.plan.controller;

import com.travel.plan.domain.User;
import com.travel.plan.exception.InvalidRequest;
import com.travel.plan.exception.InvalidSignInformation;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Login;
import com.travel.plan.response.SessionResponse;
import com.travel.plan.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){
        String accessToken = authService.singin(login);
        return new SessionResponse(accessToken);
    }

}
