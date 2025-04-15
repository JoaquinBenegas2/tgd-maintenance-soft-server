package com.tgd.maintenance_soft_server.modules.plant.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.company.dtos.CompanyResponseDto;
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
public class PlantResponseDto {

    private Long id;

    private String name;

    private String location;

    @JsonProperty("assigned_users")
    private List<UserResponseDto> assignedUsers;
}
