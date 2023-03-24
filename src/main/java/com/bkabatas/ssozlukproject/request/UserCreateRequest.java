package com.bkabatas.ssozlukproject.request;
import lombok.Data;

@Data
public class UserCreateRequest {

    private Long id ;
    private String userName;
    private String userMail;
    private String userPassword;

    private Boolean isVerified;
}
