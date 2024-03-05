package com.travel.plan.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세여.")
    private String title;
    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

//    public PostCreate(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

}
