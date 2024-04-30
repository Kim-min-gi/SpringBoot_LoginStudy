package com.travel.plan.controller;

import com.travel.plan.domain.User;
import com.travel.plan.exception.InvalidRequest;
import com.travel.plan.exception.InvalidSignInformation;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Login;
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

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public User login(@RequestBody Login login){
        log.info(">>>>>login={}", login);

        User user = userRepository.findByEmailAndPassword(login.getEmail(),login.getPassword())
                .orElseThrow(InvalidSignInformation::new);


        return user;

    }

}
