package org.example.ecommerce.dtos.user;

import lombok.Data;
import org.example.ecommerce.model.Role;

@Data
public class UpdateRoleDto {
    private String email;
    private Role newRole;
}
