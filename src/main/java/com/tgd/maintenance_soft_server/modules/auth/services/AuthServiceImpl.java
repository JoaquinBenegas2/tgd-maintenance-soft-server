package com.tgd.maintenance_soft_server.modules.auth.services;

import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getAuthenticatedUser(Jwt jwt) {
        String auth0Id = jwt.getSubject();

        return userRepository.findByAuth0Id(auth0Id)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setAuth0Id(auth0Id);
                    newUser.setName(jwt.getClaimAsString("name"));
                    newUser.setEmail(jwt.getClaimAsString("email"));

                    return userRepository.save(newUser);
                });
    }
}
