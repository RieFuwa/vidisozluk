package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.RoleDto;
import com.bkabatas.ssozlukproject.model.Role;
import com.bkabatas.ssozlukproject.request.AddRoleByUserCreateRequest;
import com.bkabatas.ssozlukproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<Void> addRole(@RequestBody Role newRole) {
        Role role = roleService.addRole(newRole);
        if(role != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getAll")
    public List<RoleDto> getAllRole(){
        return roleService.getAllRole().stream().map(u -> new RoleDto(u)).toList();
    }
    @GetMapping("/{userTypeId}")
    public RoleDto getUserTypeById(@PathVariable("userTypeId")Long userTypeId){
        Role role = roleService.getUserTypeById(userTypeId);
        return new RoleDto(role);
    }
    @PostMapping("/addRoleToUser")
    public ResponseEntity<Void> addRoleToUser(@RequestBody AddRoleByUserCreateRequest AddRoleByUserCreateRequest) {
        ResponseEntity<Object> role = roleService.addRoleToUser(AddRoleByUserCreateRequest);
        if(role != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
