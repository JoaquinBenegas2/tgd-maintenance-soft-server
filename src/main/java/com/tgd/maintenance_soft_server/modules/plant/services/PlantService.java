package com.tgd.maintenance_soft_server.modules.plant.services;

import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PlantService {

    PlantResponseDto assignUserToPlant(Long plantId, Long userId);

    PlantResponseDto unassignUserFromPlant(Long plantId, Long userId);
}
