package com.tgd.maintenance_soft_server.modules.user.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;

    @JsonProperty("auth0_id")
    private String auth0Id;

    private String email;

    private String name;

    private String image;

    private String role;
}
