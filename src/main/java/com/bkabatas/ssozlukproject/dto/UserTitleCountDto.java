package com.bkabatas.ssozlukproject.dto;

import com.bkabatas.ssozlukproject.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTitleCountDto {
    private String userName;
    private String userMail;
    private Long countTitle;

    public UserTitleCountDto(Post entity){
        this.userName=entity.getUser().getUserName();
        this.userMail=entity.getUser().getUserMail();
        this.countTitle=getCountTitle();

    }
}
