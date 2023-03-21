package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private String userMail;
    private List<Role> roles;
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
