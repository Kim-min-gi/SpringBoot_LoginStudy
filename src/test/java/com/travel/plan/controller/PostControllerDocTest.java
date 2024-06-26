package com.travel.plan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.plan.domain.Post;
import com.travel.plan.repository.PostRepository;
import com.travel.plan.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "api.study.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 단건 조회 테스트")
    void test1() throws Exception {

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);


        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("post-inquiry", pathParameters(
                            parameterWithName("postId").description("게시글 ID")
                        ),
                        PayloadDocumentation.responseFields(
                               PayloadDocumentation.fieldWithPath("id").description("게시글 ID")
                                ,PayloadDocumentation.fieldWithPath("title").description("제목")
                                ,PayloadDocumentation.fieldWithPath("content").description("내용")

                        )
                ));
    }

    @Test
    @DisplayName("글 등록")
    void test2() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("테스트제목")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("post-create", PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("title").description("제목")
                                .attributes(Attributes.key("constraint").value("좋은제목 입력해주세요.")),
                        PayloadDocumentation.fieldWithPath("content").description("내용").optional()
                )));

    }

}
