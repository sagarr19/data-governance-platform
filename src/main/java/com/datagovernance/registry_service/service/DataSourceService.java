package com.datagovernance.registry_service.service;

import com.datagovernance.registry_service.dto.request.CreateDataSourceRequest;
import com.datagovernance.registry_service.dto.response.DataSourceResponse;
import com.datagovernance.registry_service.entity.DataSourceEntity;
import com.datagovernance.registry_service.entity.enums.DataSourceStatus;
import com.datagovernance.registry_service.repository.DataSourceRepository;
import com.datagovernance.registry_service.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datagovernance.registry_service.dto.response.DataSourceSummaryResponse;
import com.datagovernance.registry_service.entity.enums.ScanStatus;
import com.datagovernance.registry_service.entity.enums.SensitivityLevel;
import com.datagovernance.registry_service.repository.ColumnRepository;
import com.datagovernance.registry_service.repository.SchemaRepository;
import com.datagovernance.registry_service.repository.TableRepository;

import java.util.Map;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataSourceService {

    private final DataSourceRepository dataSourceRepository;
    private final SchemaRepository schemaRepository;
    private final TableRepository tableRepository;
    private final ColumnRepository columnRepository;

    public DataSourceResponse createDataSource(CreateDataSourceRequest request) {
        if (dataSourceRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Data source with name '" + request.getName() + "' already exists");
        }

        DataSourceEntity entity = DataSourceEntity.builder()
                .name(request.getName())
                .type(request.getType())
                .description(request.getDescription())
                .connectionHost(request.getConnectionHost())
                .connectionPort(request.getConnectionPort())
                .databaseName(request.getDatabaseName())
                .status(DataSourceStatus.ACTIVE)
                .build();

        DataSourceEntity saved = dataSourceRepository.save(entity);
        return toResponse(saved);
    }

    public List<DataSourceResponse> getAllDataSources() {
        return dataSourceRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DataSourceResponse getDataSourceById(UUID id) {
        DataSourceEntity entity = dataSourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data source not found with id: " + id));
        return toResponse(entity);
    }

    public void deactivateDataSource(UUID id) {
        DataSourceEntity entity = dataSourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data source not found with id: " + id));
        entity.setStatus(DataSourceStatus.INACTIVE);
        dataSourceRepository.save(entity);
    }
    
    public Page<DataSourceResponse> getAllDataSourcesPaged(Pageable pageable) {
    return dataSourceRepository.findAll(pageable)
            .map(this::toResponse);
}
    public void activateDataSource(UUID id) {
    DataSourceEntity entity = dataSourceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data source not found with id: " + id));
    entity.setStatus(DataSourceStatus.ACTIVE);
    dataSourceRepository.save(entity);
    }
    
    
    private DataSourceResponse toResponse(DataSourceEntity entity) {
        return DataSourceResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .description(entity.getDescription())
                .connectionHost(entity.getConnectionHost())
                .connectionPort(entity.getConnectionPort())
                .databaseName(entity.getDatabaseName())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    public DataSourceSummaryResponse getDataSourceSummary(UUID id) {
    DataSourceEntity dataSource = dataSourceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Data source not found with id: " + id));

    long totalSchemas = schemaRepository.countByDataSourceId(id);
    long totalTables = tableRepository.countBySchemaDataSourceId(id);
    long totalColumns = columnRepository.countByTableSchemaDataSourceId(id);
    long totalPiiColumns = columnRepository.countByTableSchemaDataSourceIdAndPiiTrue(id);
    long tablesScanned = tableRepository.countBySchemaDataSourceIdAndScanStatus(id, ScanStatus.SCANNED);
    long tablesNotScanned = tableRepository.countBySchemaDataSourceIdAndScanStatus(id, ScanStatus.NOT_SCANNED);

    Map<String, Long> sensitivityBreakdown = Map.of(
            "PUBLIC", tableRepository.countBySchemaDataSourceIdAndSensitivityLevel(id, SensitivityLevel.PUBLIC),
            "INTERNAL", tableRepository.countBySchemaDataSourceIdAndSensitivityLevel(id, SensitivityLevel.INTERNAL),
            "CONFIDENTIAL", tableRepository.countBySchemaDataSourceIdAndSensitivityLevel(id, SensitivityLevel.CONFIDENTIAL),
            "RESTRICTED", tableRepository.countBySchemaDataSourceIdAndSensitivityLevel(id, SensitivityLevel.RESTRICTED)
    );

    return DataSourceSummaryResponse.builder()
            .dataSourceId(dataSource.getId())
            .dataSourceName(dataSource.getName())
            .type(dataSource.getType())
            .status(dataSource.getStatus())
            .totalSchemas(totalSchemas)
            .totalTables(totalTables)
            .totalColumns(totalColumns)
            .totalPiiColumns(totalPiiColumns)
            .tablesScanned(tablesScanned)
            .tablesNotScanned(tablesNotScanned)
            .sensitivityBreakdown(sensitivityBreakdown)
            .build();
}
}