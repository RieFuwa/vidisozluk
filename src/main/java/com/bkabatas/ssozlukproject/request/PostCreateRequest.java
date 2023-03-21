package com.bkabatas.ssozlukproject.request;
import lombok.Data;
@Data
public class PostCreateRequest {
    private  Long id;
    private Long userId;
    private Long postTypeId;
    private  String postTitle;
    private String postText;
    private  Long connectedPostId;
}
