package com.tgd.maintenance_soft_server.modules.support.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.support.dtos.ReportAProblemDto;
import com.tgd.maintenance_soft_server.modules.support.services.SupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/support")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SupportController {

    private final AuthService authService;
    private final SupportService supportService;

    @PostMapping(
            value = "/report-a-problem",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> reportAProblem(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestPart("info") @Valid ReportAProblemDto reportAProblemDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        supportService.reportAProblem(plantEntity, reportAProblemDto, images);
        return ResponseEntity.ok().build();
    }
}
