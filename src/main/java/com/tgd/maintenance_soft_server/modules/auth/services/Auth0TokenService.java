package com.tgd.maintenance_soft_server.modules.auth.services;

import org.springframework.stereotype.Service;

@Service
public interface Auth0TokenService {

    String getAccessToken();
}
