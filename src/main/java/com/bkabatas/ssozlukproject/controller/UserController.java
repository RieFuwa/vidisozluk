package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.UserCreateDto;
import com.bkabatas.ssozlukproject.dto.UserUpdateDto;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.request.UserUpdateRequest;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import com.bkabatas.ssozlukproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public  ResponseEntity<UserCreateDto> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @GetMapping("/getAll")
    @Cacheable(value = "User")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    /* unless = #userId<10*/
    @GetMapping("/{userId}")
    @Cacheable(key = "#userId",value = "User")
    public User getUserById(@PathVariable("userId")Long userId){
        return userService.getUserById(userId);
    }



    @DeleteMapping("/{userId}") //USER ID SINE GORE SILME
    public String deleteUserById(@PathVariable("userId") Long userId){
        return userService.deleteUserById(userId);
    }


    @PutMapping("/{userId}") // USER ID SINE GORE GUNCELLEME
    public ResponseEntity<UserUpdateDto> updateUserById(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest newUser){
       return userService.updateUserById(userId,newUser);
    }


}
