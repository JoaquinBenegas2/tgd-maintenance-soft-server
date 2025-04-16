package com.tgd.maintenance_soft_server.modules.auth.services;

import com.tgd.maintenance_soft_server.modules.user.dtos.UserRequestDto;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface Auth0UserService {

    UserResponseDto createUserAndAssignRole(Long userId, UserRequestDto userRequestDto);

    UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto);

    void deleteUser(Long id);
}
