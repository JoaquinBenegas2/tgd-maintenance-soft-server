package com.tgd.maintenance_soft_server.modules.plant.services.implementation;

import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.plant.repositories.PlantRepository;
import com.tgd.maintenance_soft_server.modules.plant.services.PlantService;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PlantResponseDto assignUserToPlant(Long plantId, Long userId) {
        PlantEntity plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        plant.assignUser(user);

        return modelMapper.map(plantRepository.save(plant), PlantResponseDto.class);
    }

    @Override
    @Transactional
    public PlantResponseDto unassignUserFromPlant(Long plantId, Long userId) {
        PlantEntity plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        plant.unassignUser(user);

        return modelMapper.map(plantRepository.save(plant), PlantResponseDto.class);
    }
}
