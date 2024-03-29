package com.travel.plan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.plan.domain.Post;
import com.travel.plan.repository.PostRepository;
import com.travel.plan.request.PostCreate;
import com.travel.plan.request.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("글 작성 요청시 title 값은 필수다.")
    void test2() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
//        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("{\"title\" : \"\", \"content\" : \"내용입니다.\"}")
//                        .content(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                        .param("title","글 제목입니다.")
//                        .param("content","글 내용입니다.")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세여."))
                .andDo(print());
    }

    @Test
    @DisplayName("글 작성 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                                .header("authorization","testing")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.",post.getTitle());
        assertEquals("내용입니다.",post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        //given
//        Post post1 = postRepository.save(Post.builder()
//                .title("title_1")
//                .content("content_1")
//                .build());
//
//        Post post2 = postRepository.save(Post.builder()
//                .title("title_2")
//                .content("content_2")
//                .build());
        List<Post> requstPosts = IntStream.range(1,31).mapToObj(i ->
                        Post.builder()
                                .title("테스트 제목 - " + i)
                                .content("테스트 내용 - "+i)
                                .build())
                .toList();
        postRepository.saveAll(requstPosts);


        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                //.andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("테스트 제목 - 30"))
                .andExpect(jsonPath("$[0].content").value("테스트 내용 - 30"))
                .andDo(print());

    }


    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        //given
        List<Post> requstPosts = IntStream.range(1,31).mapToObj(i ->
                        Post.builder()
                                .title("테스트 제목 - " + i)
                                .content("테스트 내용 - "+i)
                                .build())
                .toList();
        postRepository.saveAll(requstPosts);


        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("테스트 제목 - 30"))
                .andExpect(jsonPath("$[0].content").value("테스트 내용 - 30"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 제목 수정.")
    void test7() throws Exception {
        //given
        Post post1 = postRepository.save(Post.builder()
                .title("title_1")
                .content("content_1")
                .build());

        postRepository.save(post1);

        Post postEdit = postRepository.save(Post.builder()
                .title("title_2")
                .content("초가집")
                .build());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postsId}", post1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

    }



    @Test
    @DisplayName("게시글 삭제")
    void test8() throws Exception {
        //given
        Post post1 = postRepository.save(Post.builder()
                .title("title_1")
                .content("content_1")
                .build());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}",post1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 삭제")
    void test9() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception{

        PostEdit postEdit = PostEdit.builder()
                .title("title_2")
                .content("초가집")
                .build();


        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성시 제목에 '바보'는 포함될 수 없다.")
    void test11() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("나는 바보입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

}