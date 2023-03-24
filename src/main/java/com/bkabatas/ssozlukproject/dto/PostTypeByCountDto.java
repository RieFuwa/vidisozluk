package com.bkabatas.ssozlukproject.dto;

import com.bkabatas.ssozlukproject.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostTypeByCountDto {
    private String  postTypeName;
    private Long postTypeId;
    private Long countTitle;
    public PostTypeByCountDto(Post entity){
    this.postTypeName=entity.getPostType().getPostTypeName();
    this.postTypeId=entity.getPostType().getId();
    this.countTitle=getCountTitle();

    }
}
