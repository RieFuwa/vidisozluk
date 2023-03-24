package com.bkabatas.ssozlukproject.controller;

import com.bkabatas.ssozlukproject.dto.UserAuthDto;
import com.bkabatas.ssozlukproject.request.UserAuthRequest;
import com.bkabatas.ssozlukproject.request.UserRefreshRequest;
import com.bkabatas.ssozlukproject.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userAuth")
@Data
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserAuthDto login(@RequestBody UserAuthRequest loginRequest) {
        return userService.loginUser(loginRequest);

    }

    @PostMapping("/register")
    public ResponseEntity<UserAuthDto> register(@RequestBody UserAuthRequest registerRequest) {
        return userService.registerUser(registerRequest);

    }

    @PostMapping("/refresh")
    public ResponseEntity<UserAuthDto> refresh(@RequestBody UserRefreshRequest refreshRequest) {
        return userService.refreshUserToken(refreshRequest);

    }
}