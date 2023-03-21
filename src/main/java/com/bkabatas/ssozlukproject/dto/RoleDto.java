package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.Role;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String roleName;

    public RoleDto(Role entity){
        this.id=entity.getId();
        this.roleName=entity.getRoleName();
    }
}
