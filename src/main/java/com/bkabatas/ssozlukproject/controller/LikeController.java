package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.LikeDto;
import com.bkabatas.ssozlukproject.model.Like;
import com.bkabatas.ssozlukproject.request.LikeCreateRequest;
import com.bkabatas.ssozlukproject.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @GetMapping("/getAll")
    public List<LikeDto> getAllLike(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return likeService.getAllLikesWithParam(userId,postId);

    }

    @GetMapping("/{likeId}")
    public LikeDto getLikeById(@PathVariable("likeId")Long likeId){
        Like like = likeService.getLikeById(likeId);
        return new LikeDto(like);
    }
    @PostMapping("/add")
    public ResponseEntity<Void> createLike(@RequestBody LikeCreateRequest newLike) {
        Like like = likeService.createLike(newLike);
        if(like != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/{likeId}")
    public String deleteLikeById(@PathVariable("likeId") Long likeId){
        return likeService.deleteLikeById(likeId);
    }
}
