package com.tgd.maintenance_soft_server.modules.auth.services;

import com.tgd.maintenance_soft_server.modules.user.dtos.UserRequestDto;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface Auth0UserService {

    UserResponseDto createUserAndAssignRole(UserRequestDto userRequestDto, Long userId);

    String getRoleNameById(String roleId);
}
