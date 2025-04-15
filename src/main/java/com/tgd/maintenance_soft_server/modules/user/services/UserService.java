package com.tgd.maintenance_soft_server.modules.user.services;

import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<PlantResponseDto> getAssignedPlants(Long userId);

    CompanyResponseDto getAssignedCompany(Long userId);
}
