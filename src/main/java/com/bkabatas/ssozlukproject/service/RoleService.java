package com.bkabatas.ssozlukproject.service;
import com.bkabatas.ssozlukproject.model.Role;
import com.bkabatas.ssozlukproject.request.AddRoleByUserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RoleService {


    List<Role> getAllRole();

    Role getUserTypeById(Long userTypeId);

    Role addRole(Role newRole);

    ResponseEntity<Object> addRoleToUser(AddRoleByUserCreateRequest AddRoleByUserCreateRequest);
}
