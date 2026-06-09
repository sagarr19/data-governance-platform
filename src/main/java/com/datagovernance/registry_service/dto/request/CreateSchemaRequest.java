package com.datagovernance.registry_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSchemaRequest {

    @NotNull(message = "Data source ID is required")
    private UUID dataSourceId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}