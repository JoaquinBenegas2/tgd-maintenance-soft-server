package com.tgd.maintenance_soft_server.controllers;

import com.tgd.maintenance_soft_server.config.data.services.DataSeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublicController {
    private final DataSeederService dataSeederService;

    @PostMapping("/seed-data-without-maintenance")
    public ResponseEntity<String> seedDataWithoutMaintenance() {
        dataSeederService.seedDataWithoutMaintenance();
        return ResponseEntity.ok().body("Data seeded");
    }

    @PostMapping("/seed-maintenance")
    public ResponseEntity<String> seedMaintenance() {
        dataSeederService.seedMaintenance();
        return ResponseEntity.ok().body("Data seeded");
    }
}
