package com.datagovernance.registry_service.dto.response;

import com.datagovernance.registry_service.entity.enums.DataSourceStatus;
import com.datagovernance.registry_service.entity.enums.DataSourceType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class DataSourceResponse {

    private UUID id;
    private String name;
    private DataSourceType type;
    private String description;
    private String connectionHost;
    private Integer connectionPort;
    private String databaseName;
    private DataSourceStatus status;
    private LocalDateTime createdAt;
}