package com.bkabatas.ssozlukproject.service;
import com.bkabatas.ssozlukproject.model.PostType;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface PostTypeService {
    PostType createPostType(PostType newPostType);

    List<PostType> getAllPostType();

    PostType getPostTypeById(Long postTypeId);
}
