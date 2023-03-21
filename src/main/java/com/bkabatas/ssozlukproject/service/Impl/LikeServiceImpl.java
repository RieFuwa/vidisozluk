package com.bkabatas.ssozlukproject.service.Impl;

import com.bkabatas.ssozlukproject.dto.LikeDto;
import com.bkabatas.ssozlukproject.model.Like;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.repository.LikeRepository;
import com.bkabatas.ssozlukproject.request.LikeCreateRequest;
import com.bkabatas.ssozlukproject.service.LikeService;
import com.bkabatas.ssozlukproject.service.PostService;
import com.bkabatas.ssozlukproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }



    @Override
    public Like createLike(LikeCreateRequest newLike) {
        Post post = postService.getPostById(newLike.getPostId());
        User user = userService.getUserById(newLike.getUserId());

        if(user == null && post == null)
            return null;
        Like toCreate = new Like();
        toCreate.setId(newLike.getId());
        toCreate.setUser(user);
        toCreate.setPost(post);

        return likeRepository.save(toCreate);
    }

    @Override
    public String deleteLikeById(Long likeId) {
        if (!likeRepository.existsById(likeId)) {
            return "Like with id not found" +likeId+".";
        }
        likeRepository.deleteById(likeId);
        return "Like with id " +likeId+ " has been deleted success.";
    }

    @Override
    public List<LikeDto> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(like-> new LikeDto(like)).collect(Collectors.toList());
    }
}