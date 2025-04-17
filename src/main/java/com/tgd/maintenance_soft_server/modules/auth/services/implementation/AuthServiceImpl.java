package com.tgd.maintenance_soft_server.modules.auth.services.implementation;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.plant.repositories.PlantRepository;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    @Override
    public UserEntity getAuthenticatedUser(Jwt jwt) {
        String auth0Id = jwt.getSubject();

        return userRepository.findByAuth0Id(auth0Id)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setAuth0Id(auth0Id);
                    newUser.setName(jwt.getClaimAsString("name"));
                    newUser.setEmail(jwt.getClaimAsString("email"));
                    newUser.setImage(jwt.getClaimAsString("picture"));
                    newUser.setRole(jwt.getClaimAsString("role"));

                    return userRepository.save(newUser);
                });
    }

    @Override
    public PlantEntity getSelectedPlant(Long plantId) {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));
    }
}
