package com.bkabatas.ssozlukproject.request;

import lombok.Data;

@Data
public class AddRoleByUserCreateRequest {
    private Long userId;
    private Long roleId;
}
