package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.ReportDto;
import com.bkabatas.ssozlukproject.dto.UserAuthDto;
import com.bkabatas.ssozlukproject.dto.UserCreateResponse;
import com.bkabatas.ssozlukproject.dto.UserDto;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.request.UserAuthRequest;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import com.bkabatas.ssozlukproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public  ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
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
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") Long userId, @RequestBody User newUser){
        User user = userService.updateUserById(userId,newUser);
        if(user != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
