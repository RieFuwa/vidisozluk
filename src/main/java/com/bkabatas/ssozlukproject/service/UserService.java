package com.bkabatas.ssozlukproject.service;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.request.AddRoleByUserCreateRequest;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    User createUser(UserCreateRequest newUser);

    List<User> getAllUser();

    User getUserById(Long userId);

    String deleteUserById(Long userId);

    User updateUserById(Long userId, User newUser);
    void addRoleToUser(String userName,String rolName);
    User getUser(String userName);

    /*User addRoleToUser(AddRoleByUserCreateRequest addRoleByUserCreateRequest);*/
}
