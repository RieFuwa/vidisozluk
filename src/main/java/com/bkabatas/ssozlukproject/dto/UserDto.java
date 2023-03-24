package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.Role;
import com.bkabatas.ssozlukproject.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private Long id;

    private String userName;
    private List<Role> roles;
    private String userMail;
    private Date createDate;
    private Boolean isVerified;

    public UserDto(User entity){
        this.id=entity.getId();
        this.userName=entity.getUserName();
        this.userMail=entity.getUserMail();
        this.roles=getRoles();
        this.createDate=entity.getCreateDate();
        this.isVerified=entity.getIsVerified();
    }
}
