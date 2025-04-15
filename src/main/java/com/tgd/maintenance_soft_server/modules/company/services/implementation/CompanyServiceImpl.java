package com.tgd.maintenance_soft_server.modules.company.services.implementation;

import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
import com.tgd.maintenance_soft_server.modules.company.entities.CompanyEntity;
import com.tgd.maintenance_soft_server.modules.company.repositories.CompanyRepository;
import com.tgd.maintenance_soft_server.modules.company.services.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyResponseDto getCompanyById(Long companyId) {
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        return modelMapper.map(company, CompanyResponseDto.class);
    }
}
