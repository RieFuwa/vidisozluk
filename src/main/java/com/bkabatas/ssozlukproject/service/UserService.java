package com.bkabatas.ssozlukproject.service;
import com.bkabatas.ssozlukproject.dto.UserAuthDto;
import com.bkabatas.ssozlukproject.dto.UserCreateResponse;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.request.UserAuthRequest;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import com.bkabatas.ssozlukproject.request.UserRefreshRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    ResponseEntity<UserCreateResponse> createUser(UserCreateRequest newUser);

    List<User> getAllUser();

    User getUserById(Long userId);

    String deleteUserById(Long userId);

    User updateUserById(Long userId, User newUser);

    Object addRoleToUser(Long userId, Long roleId);
    ResponseEntity<UserAuthDto> refreshUserToken(UserRefreshRequest refreshRequest);

    ResponseEntity<UserAuthDto> registerUser(UserAuthRequest registerRequest);

    UserAuthDto loginUser(UserAuthRequest loginRequest);
}
