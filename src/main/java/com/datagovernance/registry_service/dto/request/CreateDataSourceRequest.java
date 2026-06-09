package com.datagovernance.registry_service.dto.request;

import com.datagovernance.registry_service.entity.enums.DataSourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDataSourceRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private DataSourceType type;

    private String description;
    private String connectionHost;
    private Integer connectionPort;
    private String databaseName;
}