package com.tgd.maintenance_soft_server.modules.user.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String email;

    private String name;

    @JsonProperty("role_id")
    private String roleId;

    private String password;
}
