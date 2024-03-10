package com.travel.plan.service;

import com.travel.plan.domain.Post;
import com.travel.plan.repository.PostRepository;
import com.travel.plan.request.PostCreate;
import com.travel.plan.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertEquals(1L, postRepository.count());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        //given
        Post requstPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requstPost);

        //when
        PostResponse response = postService.get(requstPost.getId());

        //then
        assertNotNull(response);
        Assertions.assertEquals(1L, postRepository.count());
        assertEquals("foo",response.getTitle());
        assertEquals("bar",response.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3(){
        //given
        Post requstPost1 = Post.builder()
                .title("foo1")
                .content("bar1")
                .build();

        postRepository.saveAll(List.of(
                Post.builder()
                        .title("foo1")
                        .content("bar1")
                        .build(),
                Post.builder()
                        .title("foo2")
                        .content("bar2")
                        .build()

        ));

        //when
        List<PostResponse> posts = postService.getList();

        //then
        Assertions.assertEquals(2L, posts.size());
    }

    @Test
    @DisplayName("글 삭제")
    void test6(){
        //given
        Post requstPost1 = Post.builder()
                .title("테스트1")
                .content("반포자이")
                .build();

        postRepository.save(requstPost1);

        //when
        postService.delete(requstPost1.getId());

        //then
        Assertions.assertEquals(0, postRepository.count());
    }


}