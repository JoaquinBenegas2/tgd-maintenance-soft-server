package com.tgd.maintenance_soft_server.modules.auth.services.implementation;

import com.tgd.maintenance_soft_server.modules.auth.services.Auth0TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class Auth0TokenServiceImpl implements Auth0TokenService {

    private final RestTemplate restTemplate;

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.client-id}")
    private String clientId;

    @Value("${auth0.client-secret}")
    private String clientSecret;

    public String getAccessToken() {
        String url = "https://" + domain + "/oauth/token";

        Map<String, String> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "audience", "https://" + domain + "/api/v2/",
                "grant_type", "client_credentials"
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);

        return (String) response.getBody().get("access_token");
    }
}
