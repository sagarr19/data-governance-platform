package com.datagovernance.registry_service.dto.request;

import com.datagovernance.registry_service.entity.enums.SensitivityLabel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@Data
public class CreateColumnRequest {

    @NotNull(message = "Table ID is required")
    private UUID tableId;

    @NotBlank(message = "Name is required")
    private String name;

    private String dataType;

    private SensitivityLabel sensitivityLabel;
    
    @JsonProperty("isPii")
    private boolean isPii;
}