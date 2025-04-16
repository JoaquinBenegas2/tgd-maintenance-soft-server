package com.tgd.maintenance_soft_server.modules.auth.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgd.maintenance_soft_server.modules.auth.services.Auth0TokenService;
import com.tgd.maintenance_soft_server.modules.auth.services.Auth0UserService;
import com.tgd.maintenance_soft_server.modules.company.repositories.CompanyRepository;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserRequestDto;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Auth0UserServiceImpl implements Auth0UserService {

    private final RestTemplate restTemplate;
    private final Auth0TokenService auth0TokenService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;

    @Value("${auth0.domain}")
    private String domain;

    public UserResponseDto createUserAndAssignRole(Long userId, UserRequestDto userRequestDto) {
        // Create user in Auth0
        String token = auth0TokenService.getAccessToken();

        String url = "https://" + domain + "/api/v2/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "email", userRequestDto.getEmail(),
                "name", userRequestDto.getName(),
                "password", userRequestDto.getPassword(),
                "connection", "Username-Password-Authentication",
                "email_verified", false
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        Map responseBody = response.getBody();

        if (responseBody == null || !responseBody.containsKey("user_id")) {
            throw new RuntimeException("No se pudo crear el usuario en Auth0");
        }

        String auth0Id = (String) responseBody.get("user_id");
        String picture = (String) responseBody.get("picture");

        // Assign role to user
        String roleUrl = "https://" + domain + "/api/v2/roles/" + userRequestDto.getRoleId() + "/users";

        HttpHeaders roleHeaders = new HttpHeaders();
        roleHeaders.setBearerAuth(token);
        roleHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, List<String>> roleBody = Map.of("users", List.of(auth0Id));
        HttpEntity<Map<String, List<String>>> roleRequest = new HttpEntity<>(roleBody, roleHeaders);
        restTemplate.postForEntity(roleUrl, roleRequest, String.class);

        String roleName = getRoleNameById(userRequestDto.getRoleId());

        // Save user in the database
        UserEntity requestUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserEntity userEntity = new UserEntity();
        userEntity.setAuth0Id(auth0Id);
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setName(userRequestDto.getEmail());
        userEntity.setRole(roleName);
        userEntity.setImage(picture);
        userEntity.setCompany(requestUser.getCompany());

        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String token = auth0TokenService.getAccessToken();

        String encodedId = URLEncoder.encode(user.getAuth0Id(), StandardCharsets.UTF_8);
        String url = "https://" + domain + "/api/v2/users/" + encodedId;

        Map<String, Object> body = new HashMap<>();
        body.put("email", userRequestDto.getEmail());
        body.put("name", userRequestDto.getName());

        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isBlank()) {
            body.put("password", userRequestDto.getPassword());
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            String requestBody = new ObjectMapper().writeValueAsString(body);

            HttpRequest patchRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new RuntimeException("Failed to update user in Auth0: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while updating user in Auth0", e);
        }

        // Update role
        String newRoleId = userRequestDto.getRoleId();
        if (newRoleId != null && !newRoleId.isBlank()) {
            // First we remove the current role
            removeAllRolesFromUser(user.getAuth0Id(), token);
            // Then we assign the new role
            assignRoleToUser(user.getAuth0Id(), newRoleId, token);
            String roleName = getRoleNameById(newRoleId);
            user.setRole(roleName);
        }

        user.setEmail(userRequestDto.getEmail());
        user.setName(userRequestDto.getName());
        user = userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String token = auth0TokenService.getAccessToken();
        String url = "https://" + domain + "/api/v2/users/" + user.getAuth0Id();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);

        userRepository.deleteById(id);
    }


    public void assignRoleToUser(String auth0Id, String roleId, String token) {
        String roleUrl = "https://" + domain + "/api/v2/roles/" + roleId + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, List<String>> body = Map.of("users", List.of(auth0Id));
        HttpEntity<Map<String, List<String>>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(roleUrl, request, String.class);
    }

    public void removeAllRolesFromUser(String auth0Id, String token) {
        String url = "https://" + domain + "/api/v2/users/" + auth0Id + "/roles";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
        List<Map<String, Object>> roles = response.getBody();

        if (roles != null && !roles.isEmpty()) {
            List<String> roleIds = roles.stream()
                    .map(role -> (String) role.get("id"))
                    .toList();

            HttpHeaders deleteHeaders = new HttpHeaders();
            deleteHeaders.setBearerAuth(token);
            deleteHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, List<String>>> deleteRequest =
                    new HttpEntity<>(Map.of("roles", roleIds), deleteHeaders);

            restTemplate.exchange(url, HttpMethod.DELETE, deleteRequest, Void.class);
        }
    }

    public String getRoleNameById(String roleId) {
        String url = "https://" + domain + "/api/v2/roles/" + roleId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(auth0TokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return (String) response.getBody().get("name");
    }
}
