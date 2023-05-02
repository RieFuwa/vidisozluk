package com.bkabatas.ssozlukproject.service.Impl;
import com.bkabatas.ssozlukproject.dto.UserAuthDto;
import com.bkabatas.ssozlukproject.dto.UserCreateDto;
import com.bkabatas.ssozlukproject.dto.UserUpdateDto;
import com.bkabatas.ssozlukproject.model.Role;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.model.UserRefreshToken;
import com.bkabatas.ssozlukproject.repository.RoleRepository;
import com.bkabatas.ssozlukproject.repository.UserRepository;
import com.bkabatas.ssozlukproject.request.UserUpdateRequest;
import com.bkabatas.ssozlukproject.request.UserAuthRequest;
import com.bkabatas.ssozlukproject.request.UserCreateRequest;
import com.bkabatas.ssozlukproject.request.UserRefreshRequest;
import com.bkabatas.ssozlukproject.security.JwtTokenProvider;
import com.bkabatas.ssozlukproject.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRefreshTokenServiceImpl refreshTokenService;


    private  JavaMailSender mailSender;

    public UserServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public ResponseEntity<UserCreateDto> createUser(UserCreateRequest newUser) {
        UserCreateDto userCreateDto = new UserCreateDto();
        User toCreate= new User();
        if(userRepository.findByUserMail(newUser.getUserMail()) != null) {
            userCreateDto.setMessage("Mail already in use.");
            return new ResponseEntity<>(userCreateDto,HttpStatus.BAD_REQUEST);
        }
        toCreate.setId(newUser.getId());
        toCreate.setUserName(newUser.getUserName());
        toCreate.setUserMail(newUser.getUserMail());
        toCreate.setRoles(new ArrayList<>());
        toCreate.setCreateDate(new Date());
        toCreate.setUpdateDate(new Date());
        toCreate.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
        toCreate.setIsVerified(newUser.getIsVerified());

         userRepository.save(toCreate);
        userCreateDto.setMessage("User successfully created.");
        userCreateDto.setUserId(toCreate.getId());
        return new ResponseEntity<>(userCreateDto, HttpStatus.CREATED);
    }
    @Override
    public Object addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("Could not found with id"));
        Role role = roleRepository.findById(roleId).orElseThrow(()->new RuntimeException("Could not found with id"));;
        if(role== null && user ==null){
            return null;
        }
        return user.getRoles().add(role);


    }
    @Override
    public List<User> getAllUser() {
        return  userRepository.findAll();
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
    public String emailCheckUserById(String userMail) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUserMail(userMail));
        if(user.isPresent()){
            String url =  "http://localhost:3000/user/changepassword";
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("bedirhanboshesap@gmail.com");
            simpleMailMessage.setTo(user.get().getUserMail());
            simpleMailMessage.setSubject("VıdıSözlük Şifre Değiştirme Maili");
            simpleMailMessage.setText("Şifre değiştirmek için "+ url +" linkine tıklayınız.");
            this.mailSender.send(simpleMailMessage);
            return "Şifre değiştirmek için girdiğiniz email adresine mail gönderilmiştir.";
        }else
            return "Girdiğiniz email bulunamadı.";
    }
    @Override
    public ResponseEntity<UserUpdateDto> updateUserPasswordById(String userMail, UserUpdateRequest updateUser) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        Optional<User> user = Optional.ofNullable(userRepository.findByUserMail(userMail));
            User foundUser = user.get();
            foundUser.getUserName();
            foundUser.getUserMail();
            foundUser.getIsVerified();
            foundUser.getCreateDate();
            foundUser.setUserPassword(passwordEncoder.encode(updateUser.getUserPassword()));
            foundUser.setUpdateDate(new Date());
            userUpdateDto.setMessage("User password changed.");
            userUpdateDto.setUserId(foundUser.getId());
            userRepository.save(foundUser);
            return new ResponseEntity<>(userUpdateDto, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<UserAuthDto> refreshUserToken(UserRefreshRequest refreshRequest) {
        UserAuthDto response = new UserAuthDto();
        UserRefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
        if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token)) {
            User user = token.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
            response.setMessage("token successfully refreshed.");
            response.setAccessToken("Bearer " + jwtToken);
            response.setUserId(user.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("refresh token is not valid.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<UserAuthDto> registerUser(UserAuthRequest registerRequest) {
        UserAuthDto authResponse = new UserAuthDto();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUserMail(), registerRequest.getUserPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        User user = userRepository.findByUserMail(registerRequest.getUserMail());
        authResponse.setMessage("User successfully registered.");
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());

         if (user.getRoles().stream().anyMatch(key->"ROLE_ADMIN".equals(key.getRoleName()))){
             SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
             simpleMailMessage.setFrom("bedirhanboshesap@gmail.com");
             simpleMailMessage.setTo("bedirhanboshesap@gmail.com");
             simpleMailMessage.setSubject("VıdıSözlük Uygulamasına Admin Girişi yapıldı.");
             simpleMailMessage.setText("VıdıSözlük Uygulamasına "+  new Date() +" tarihinde \n"+ user.getId()+" id'li kişi tarafından admin girişi yapıldı.");
             this.mailSender.send(simpleMailMessage);
             return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
         }

       return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @Override
    public UserAuthDto loginUser(UserAuthRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserMail(), loginRequest.getUserPassword());
        org.springframework.security.core.Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        User user = userRepository.findByUserMail(loginRequest.getUserMail());

        UserAuthDto authResponse = new UserAuthDto();
        authResponse.setMessage("User successfully login.");
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());

        return authResponse;
    }



}
