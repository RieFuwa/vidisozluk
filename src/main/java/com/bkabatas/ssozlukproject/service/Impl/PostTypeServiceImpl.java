package com.bkabatas.ssozlukproject.service.Impl;
import com.bkabatas.ssozlukproject.model.PostType;
import com.bkabatas.ssozlukproject.repository.PostTypeRepository;
import com.bkabatas.ssozlukproject.service.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PostTypeServiceImpl implements PostTypeService {

    @Autowired
    private PostTypeRepository postTypeRepository;
    @Override
    public PostType createPostType(PostType newPostType) {
        return postTypeRepository.save(newPostType);
    }

    @Override
    public List<PostType> getAllPostType() {
        return postTypeRepository.findAll();
    }

    @Override
    public PostType getPostTypeById(Long postTypeId) {
        return postTypeRepository.findById(postTypeId).orElse(null);
    }
}
