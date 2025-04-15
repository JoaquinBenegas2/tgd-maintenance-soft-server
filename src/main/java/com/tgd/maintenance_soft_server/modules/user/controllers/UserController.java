package com.tgd.maintenance_soft_server.modules.user.controllers;

import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to user management")
public class UserController {

    private final UserService userService;

    @GetMapping("/{auth0Id}/plants")
    @Operation(description = "Retrieves the list of plants to which the user is assigned")
    public ResponseEntity<List<PlantResponseDto>> getAssignedPlants(@PathVariable String auth0Id) {
        return ResponseEntity.ok(userService.getAssignedPlants(auth0Id));
    }
}
