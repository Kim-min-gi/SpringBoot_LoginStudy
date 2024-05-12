package com.travel.plan.controller;

import com.travel.plan.config.AppConfig;
import com.travel.plan.request.Login;
import com.travel.plan.request.Signup;
import com.travel.plan.response.SessionResponse;
import com.travel.plan.service.AuthService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final AppConfig appConfig;


    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){
        Long userId = authService.singin(login);

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(appConfig.getJwtKey())
                .issuedAt(new Date())
                .compact();

//        byte[] encodingKey = key.getEncoded();
//        String strKey = Base64.getEncoder().encodeToString(encodingKey);

        //return new SessionResponse(jws);


//        ResponseCookie cookie = ResponseCookie.from("SESSION",accessToken)
//                .domain("localhost") // todo 서버 환경에 따른 분리 필요
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> cookie = {}", cookie);

        //return new SessionResponse(accessToken);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE,cookie.toString())
//                .build();

          return new SessionResponse(jws);
    }


    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }

}
