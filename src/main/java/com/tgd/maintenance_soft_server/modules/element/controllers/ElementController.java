package com.tgd.maintenance_soft_server.modules.element.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.element.services.ElementService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/elements")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ElementController {

    private final AuthService authService;
    private final ElementService elementService;

    @DeleteMapping("/{id}")
    public void deleteElementById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        elementService.deleteElementById(id, plantEntity);
    }
}
