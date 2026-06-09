package com.datagovernance.registry_service.dto.response;

import com.datagovernance.registry_service.entity.enums.SensitivityLabel;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ColumnResponse {

    private UUID id;
    private UUID tableId;
    private String tableName;
    private String name;
    private String dataType;
    private SensitivityLabel sensitivityLabel;
    private boolean isPii;
}