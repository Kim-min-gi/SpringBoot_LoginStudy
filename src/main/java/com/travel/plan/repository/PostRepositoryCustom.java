package com.travel.plan.repository;

import com.travel.plan.domain.Post;
import com.travel.plan.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
