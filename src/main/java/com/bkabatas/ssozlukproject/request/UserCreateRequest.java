package com.bkabatas.ssozlukproject.request;
import com.bkabatas.ssozlukproject.model.Role;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UserCreateRequest {

    private Long id ;
    private String userName;
    private String userMail;
    private String userPassword;

    private Boolean isVerified;
}
