package com.bkabatas.ssozlukproject.dto;

import lombok.Data;

@Data
public class UserAuthDto {
    private String message;
    private Long userId;
    private String accessToken;
    private String refreshToken;

}
