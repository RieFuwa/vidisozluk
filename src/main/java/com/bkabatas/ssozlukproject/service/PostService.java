package com.bkabatas.ssozlukproject.service;
import com.bkabatas.ssozlukproject.dto.PostDto;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.request.PostCreateRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface PostService {
    Post createPost(PostCreateRequest newPost);



    Post getPostById(Long postId);

    String deletePostById(Long postId);

    List<PostDto> getAllUserPost(Optional<Long> userId);

    List<PostDto> getPostAnswersByPostId(Optional<Long> postId);

    PostDto getOnePostByIdWithLikes(Long postId);
}
