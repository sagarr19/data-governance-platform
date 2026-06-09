package com.datagovernance.registry_service.dto.response;

import com.datagovernance.registry_service.entity.enums.DataSourceStatus;
import com.datagovernance.registry_service.entity.enums.DataSourceType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class DataSourceSummaryResponse {

    private UUID dataSourceId;
    private String dataSourceName;
    private DataSourceType type;
    private DataSourceStatus status;
    private long totalSchemas;
    private long totalTables;
    private long totalColumns;
    private long totalPiiColumns;
    private long tablesScanned;
    private long tablesNotScanned;
    private Map<String, Long> sensitivityBreakdown;
}