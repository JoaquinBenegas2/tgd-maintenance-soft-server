package com.tgd.maintenance_soft_server.modules.plant.controllers;

import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.services.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Plants", description = "Operations related to plant management")
public class PlantController {

    private final PlantService plantService;

    @PostMapping("/{plantId}/users/{userId}")
    @Operation(description = "Assigns a user to a plant")
    public ResponseEntity<PlantResponseDto> assignUserToPlant(
            @PathVariable Long plantId,
            @PathVariable Long userId
    ) {
        PlantResponseDto updatedPlant = plantService.assignUserToPlant(plantId, userId);
        return ResponseEntity.ok(updatedPlant);
    }

    @DeleteMapping("/{plantId}/users/{userId}")
    @Operation(description = "Unassigns a user from a plant")
    public ResponseEntity<PlantResponseDto> unassignUserFromPlant(
            @PathVariable Long plantId,
            @PathVariable Long userId
    ) {
        PlantResponseDto updatedPlant = plantService.unassignUserFromPlant(plantId, userId);
        return ResponseEntity.ok(updatedPlant);
    }
}
