package com.bkabatas.ssozlukproject.service;

import com.bkabatas.ssozlukproject.dto.LikeDto;
import com.bkabatas.ssozlukproject.model.Like;
import com.bkabatas.ssozlukproject.request.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface LikeService {
    Like getLikeById(Long likeId);

    Like createLike(LikeCreateRequest newLike);

    String deleteLikeById(Long likeId);

    List<LikeDto> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId);
}
