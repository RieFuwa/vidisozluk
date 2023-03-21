package com.bkabatas.ssozlukproject.service.Impl;
import com.bkabatas.ssozlukproject.model.Role;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.repository.RoleRepository;
import com.bkabatas.ssozlukproject.repository.UserRepository;
import com.bkabatas.ssozlukproject.request.AddRoleByUserCreateRequest;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import com.bkabatas.ssozlukproject.service.UserService;
import com.bkabatas.ssozlukproject.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional @Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User createUser(UserCreateRequest newUser) {
        User toCreate= new User();
        toCreate.setId(newUser.getId());
        toCreate.setName(newUser.getName());
        toCreate.setUserName(newUser.getUserName());
        toCreate.setUserMail(newUser.getUserMail());
        toCreate.setRoles(new ArrayList<>());
        toCreate.setCreateDate(new Date());
        toCreate.setUserPassword(newUser.getUserPassword());
        toCreate.setIsVerified(newUser.getIsVerified());
        return userRepository.save(toCreate);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    @Override
    public String deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            return "User with id not found" +userId+".";
        }
        userRepository.deleteById(userId);
        return "User with id " +userId+ " has been deleted success.";
    }


    @Override
    public User updateUserById(Long userId, User newUser) {
        Optional<User> user =userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setUserMail(newUser.getUserMail());
            foundUser.setUserPassword(newUser.getUserPassword());
            return userRepository.save(foundUser);
        }else
            return null;
    }

    @Override
    public void addRoleToUser(String userName, String rolName) {
       User user = userRepository.findByUserName(userName);
       Role role = roleRepository.findByRoleName(rolName);
       user.getRoles().add(role);
    }

    @Override
    public User getUser(String userName) {
        return  userRepository.findByUserName(userName);
    }

  /*  @Override
    public User addRoleToUser(AddRoleByUserCreateRequest addRoleByUserCreateRequest) {

        User user = (User) userRepository.findByUserName(addRoleByUserCreateRequ);

            List<Role> role=new ArrayList<>();
            Role roleAs = new Role();
            roleAs.setRoleName(addRoleByUserCreateRequest.getRoleName());
            role.add(roleAs);

            User newUser = user.get();
            newUser.setName(addRoleByUserCreateRequest.getName());
            newUser.setRoles(role);
            return  userRepository.save(newUser);

    }
*/
}
