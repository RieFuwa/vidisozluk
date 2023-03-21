package com.bkabatas.ssozlukproject.dto;

import com.bkabatas.ssozlukproject.model.Like;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.User;
import lombok.Data;

@Data
public class LikeDto {
    private Long id ;
    private Long userId;
    private Long postId;

    public  LikeDto(Like entity){
        this.id=entity.getId();
        this.userId=entity.getUser().getId();
        this.postId=entity.getPost().getId();
    }
}
