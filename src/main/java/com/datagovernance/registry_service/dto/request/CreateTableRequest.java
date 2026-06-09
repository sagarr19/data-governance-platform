package com.datagovernance.registry_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateTableRequest {

    @NotNull(message = "Schema ID is required")
    private UUID schemaId;

    @NotBlank(message = "Name is required")
    private String name;

    private Long rowCount;
}