package com.tgd.maintenance_soft_server.modules.auth.services.implementation;

import com.tgd.maintenance_soft_server.modules.auth.services.Auth0TokenService;
import com.tgd.maintenance_soft_server.modules.auth.services.Auth0UserService;
import com.tgd.maintenance_soft_server.modules.company.entities.CompanyEntity;
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

    public UserResponseDto createUserAndAssignRole(UserRequestDto userRequestDto, Long userId) {
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
    public String getRoleNameById(String roleId) {
        String url = "https://" + domain + "/api/v2/roles/" + roleId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(auth0TokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return (String) response.getBody().get("name");
    }
}
