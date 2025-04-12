package com.tgd.maintenance_soft_server.modules.auth.services;

import com.tgd.maintenance_soft_server.modules.users.entities.UserEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserEntity getAuthenticatedUser(Jwt jwt);
}
