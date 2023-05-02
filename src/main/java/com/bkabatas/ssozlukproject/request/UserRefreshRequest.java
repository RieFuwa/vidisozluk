package com.bkabatas.ssozlukproject.request;

import lombok.Data;

@Data
public class UserRefreshRequest {
    private Long userId;
    private String refreshToken;
}
