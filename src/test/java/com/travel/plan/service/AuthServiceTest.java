package com.travel.plan.service;

import com.travel.plan.domain.User;
import com.travel.plan.exception.AlreadyExistsEmailException;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Login;
import com.travel.plan.request.Signup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.rmi.AlreadyBoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1(){
        //give
        Signup signup =  Signup.builder()
                .email("testing@gmail.com")
                .name("testing")
                .password("1234")
                .build();

        //when
        authService.signup(signup);

        //then
        Assertions.assertEquals(1,userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("testing@gmail.com",user.getEmail());
        assertEquals("testing",user.getName());
        assertEquals("1234",user.getPassword());

    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2(){
        //give
        User user = User.builder()
                .email("testing@gmail.com")
                .password("1234")
                .name("testing")
                .build();

        userRepository.save(user);

        Signup signup =  Signup.builder()
                .email("testing@gmail.com")
                .name("testing")
                .password("1234")
                .build();

        //expected
        Assertions.assertThrows(AlreadyExistsEmailException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authService.signup(signup);
            }
        });

        //then
        Assertions.assertEquals(1,userRepository.count());


    }


}