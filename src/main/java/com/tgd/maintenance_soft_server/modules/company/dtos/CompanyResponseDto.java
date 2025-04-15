package com.tgd.maintenance_soft_server.modules.company.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {

    private Long id;

    private String name;

    @JsonProperty("assigned_users")
    private List<UserResponseDto> assignedUsers;
}
