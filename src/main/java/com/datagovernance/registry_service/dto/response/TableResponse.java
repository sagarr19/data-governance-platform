package com.datagovernance.registry_service.dto.response;

import com.datagovernance.registry_service.entity.enums.ScanStatus;
import com.datagovernance.registry_service.entity.enums.SensitivityLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TableResponse {

    private UUID id;
    private UUID schemaId;
    private String schemaName;
    private String name;
    private Long rowCount;
    private SensitivityLevel sensitivityLevel;
    private ScanStatus scanStatus;
    private LocalDateTime createdAt;
}