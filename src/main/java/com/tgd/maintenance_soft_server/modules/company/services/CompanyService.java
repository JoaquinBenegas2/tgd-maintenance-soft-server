package com.tgd.maintenance_soft_server.modules.company.services;

import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    CompanyResponseDto getCompanyById(Long companyId);
}
