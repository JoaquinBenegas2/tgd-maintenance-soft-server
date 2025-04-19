package com.tgd.maintenance_soft_server.modules.component.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.component.services.ComponentService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/components")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ComponentController {

    private final AuthService authService;
    private final ComponentService componentService;

    @DeleteMapping("/{id}")
    public void deleteComponentById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        componentService.deleteComponentById(id, plantEntity);
    }
}
