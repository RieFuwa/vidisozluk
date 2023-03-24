package com.bkabatas.ssozlukproject.request;

import lombok.Data;

@Data
public class UserAuthRequest {

    private String userPassword;
    private String userMail;

}