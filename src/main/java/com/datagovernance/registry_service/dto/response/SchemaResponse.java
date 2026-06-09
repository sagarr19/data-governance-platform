package com.datagovernance.registry_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SchemaResponse {

    private UUID id;
    private UUID dataSourceId;
    private String dataSourceName;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}