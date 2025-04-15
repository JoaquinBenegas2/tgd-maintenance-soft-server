package com.tgd.maintenance_soft_server.modules.user.services.implementation;

import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import com.tgd.maintenance_soft_server.modules.user.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PlantResponseDto> getAssignedPlants(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return user.getAssignedPlants().stream()
                .map(plant -> modelMapper.map(plant, PlantResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponseDto getAssignedCompany(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return modelMapper.map(user.getCompany(), CompanyResponseDto.class);
    }
}
