package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.ReportDto;
import com.bkabatas.ssozlukproject.dto.UserCreateResponse;
import com.bkabatas.ssozlukproject.dto.UserDto;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import com.bkabatas.ssozlukproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest newUser) {
        ResponseEntity<UserCreateResponse> user = userService.createUser(newUser);
        if(user != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAll")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId")Long userId){
        User user= userService.getUserById(userId);
        return new UserDto(user);
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
