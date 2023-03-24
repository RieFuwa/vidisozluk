package com.bkabatas.ssozlukproject.service.Impl;

import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.repository.UserRepository;
import com.bkabatas.ssozlukproject.security.JwtUserDetails;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        User user =userRepository.findByUserMail(userMail);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return JwtUserDetails.create(user);
    }
}
