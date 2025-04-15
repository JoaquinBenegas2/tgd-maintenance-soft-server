package com.tgd.maintenance_soft_server.modules.company.controllers;

import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
import com.tgd.maintenance_soft_server.modules.company.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Companies", description = "Operations related to company management")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    @Operation(description = "Retrieves a company by its ID")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }
}
