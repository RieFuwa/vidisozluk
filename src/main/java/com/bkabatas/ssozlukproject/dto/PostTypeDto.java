package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.PostType;
import lombok.Data;

@Data
public class PostTypeDto {
    private Long id;
    private String postTypeName;

    public PostTypeDto(PostType entity){
        this.id=entity.getId();
        this.postTypeName=entity.getPostTypeName();
    }
}
