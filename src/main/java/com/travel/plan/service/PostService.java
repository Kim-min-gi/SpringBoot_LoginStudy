package com.travel.plan.service;

import com.travel.plan.domain.Post;
import com.travel.plan.domain.PostEditor;
import com.travel.plan.exception.PostNotFound;
import com.travel.plan.repository.PostRepository;
import com.travel.plan.request.PostCreate;
import com.travel.plan.request.PostEdit;
import com.travel.plan.request.PostSearch;
import com.travel.plan.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
       Post post = Post.builder()
               .title(postCreate.getTitle())
               .content(postCreate.getContent())
               .build();
       postRepository.save(post);
    }


    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        //Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));

        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

//        if (postEdit.getTitle() != null){
//            editorBuilder.title(postEdit.getTitle());
//        }
//        if (postEdit.getContent() != null){
//            editorBuilder.title(postEdit.getContent());
//        }

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        //return new PostResponse(post);

    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);

    }
}
