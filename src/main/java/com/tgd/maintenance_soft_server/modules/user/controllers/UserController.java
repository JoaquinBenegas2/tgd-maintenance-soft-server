package com.tgd.maintenance_soft_server.modules.user.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.Auth0UserService;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserRequestDto;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to user management")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final Auth0UserService auth0UserService;

    @GetMapping("/plants")
    @Operation(description = "Retrieves the list of plants to which the user is assigned")
    public ResponseEntity<List<PlantResponseDto>> getAssignedPlants(@AuthenticationPrincipal Jwt jwt) {
        UserEntity user = authService.getAuthenticatedUser(jwt);
        return ResponseEntity.ok(userService.getAssignedPlants(user.getId()));
    }

    @GetMapping("/company")
    @Operation(description = "Retrieves the company to which the user is assigned")
    public ResponseEntity<CompanyResponseDto> getAssignedCompany(@AuthenticationPrincipal Jwt jwt) {
        UserEntity user = authService.getAuthenticatedUser(jwt);
        return ResponseEntity.ok(userService.getAssignedCompany(user.getId()));
    }

    @PostMapping
    @Operation(description = "Creates a new user in Auth0 and assigns a role")
    public ResponseEntity<UserResponseDto> createUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserRequestDto userRequestDto) {
        UserEntity user = authService.getAuthenticatedUser(jwt);
        return ResponseEntity.ok(auth0UserService.createUserAndAssignRole(user.getId(), userRequestDto));
    }

    @Operation(description = "Updates a user in Auth0 and in the local database")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(auth0UserService.updateUser(id, userRequestDto));
    }

    @Operation(description = "Deletes a user from Auth0 and from the local database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        auth0UserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
