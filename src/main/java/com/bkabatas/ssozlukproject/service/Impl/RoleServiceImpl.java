package com.bkabatas.ssozlukproject.service.Impl;
import com.bkabatas.ssozlukproject.model.Role;
import com.bkabatas.ssozlukproject.repository.RoleRepository;
import com.bkabatas.ssozlukproject.repository.UserRepository;
import com.bkabatas.ssozlukproject.request.AddRoleByUserCreateRequest;
import com.bkabatas.ssozlukproject.service.RoleService;
import com.bkabatas.ssozlukproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;



    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getUserTypeById(Long userTypeId) {
        return roleRepository.findById(userTypeId).orElse(null);
    }

    @Override
    public Role addRole(Role newRole) {
        return roleRepository.save(newRole);
    }

    @Override
    public ResponseEntity<Object> addRoleToUser(AddRoleByUserCreateRequest addRoleByUserCreateRequest) {
        userService.addRoleToUser(addRoleByUserCreateRequest.getName(),addRoleByUserCreateRequest.getRoleName());
        return ResponseEntity.ok().build();
    }
}
