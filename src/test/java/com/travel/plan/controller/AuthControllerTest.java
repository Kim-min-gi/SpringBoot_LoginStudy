package com.travel.plan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.plan.domain.User;
import com.travel.plan.repository.SessionRepository;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Login;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공")
    void test() throws Exception{

        userRepository.save(User.builder()
                        .name("test")
                        .email("Testing@naver.com")
                        .password("1234")
                .build());


        Login login = Login.builder()
                .email("Testing@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


    }


    @Test
    @Transactional
    @DisplayName("로그인 성공후 세션 한개 추가")
    void test2() throws Exception{

        User user = userRepository.save(User.builder()
                .name("test")
                .email("Testing@naver.com")
                .password("1234")
                .build());


        Login login = Login.builder()
                .email("Testing@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

//        User loggedInUser = userRepository.findById(user.getId())
//                        .orElseThrow(RuntimeException::new);


        //Assertions.assertEquals(1L,sessionRepository.count());
        Assertions.assertEquals(1L,user.getSession().size());

    }

    @Test
    @DisplayName("로그인 성공후 세션 응답")
    void test3() throws Exception{

        User user = userRepository.save(User.builder()
                .name("test")
                .email("Testing@naver.com")
                .password("1234")
                .build());


        Login login = Login.builder()
                .email("Testing@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속한다.")
    void test4() throws Exception{
//        User user = userRepository.save(User.builder()
//                .name("test")
//                .email("Testing@naver.com")
//                .password("1234")
//                .build());
//
//
//        Login login = Login.builder()
//                .email("Testing@naver.com")
//                .password("1234")
//                .build();
//
//        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/foo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());


    }


}