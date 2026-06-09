package com.datagovernance.registry_service.service;

import com.datagovernance.registry_service.dto.request.CreateTableRequest;
import com.datagovernance.registry_service.dto.response.TableResponse;
import com.datagovernance.registry_service.entity.SchemaEntity;
import com.datagovernance.registry_service.entity.TableEntity;
import com.datagovernance.registry_service.entity.enums.ScanStatus;
import com.datagovernance.registry_service.entity.enums.SensitivityLevel;
import com.datagovernance.registry_service.repository.SchemaRepository;
import com.datagovernance.registry_service.repository.TableRepository;
import com.datagovernance.registry_service.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;
    private final SchemaRepository schemaRepository;

    public TableResponse createTable(CreateTableRequest request) {
        SchemaEntity schema = schemaRepository.findById(request.getSchemaId())
                .orElseThrow(() -> new ResourceNotFoundException("Schema not found with id: " + request.getSchemaId()));

        if (tableRepository.existsBySchemaIdAndName(request.getSchemaId(), request.getName())) {
            throw new DuplicateResourceException("Table '" + request.getName() + "' already exists in this schema");
        }

        TableEntity entity = TableEntity.builder()
                .schema(schema)
                .name(request.getName())
                .rowCount(request.getRowCount())
                .sensitivityLevel(SensitivityLevel.PUBLIC)
                .scanStatus(ScanStatus.NOT_SCANNED)
                .build();

        TableEntity saved = tableRepository.save(entity);
        return toResponse(saved);
    }

public Page<TableResponse> getTablesBySchemaPaged(UUID schemaId, Pageable pageable) {
    if (!schemaRepository.existsById(schemaId)) {
        throw new ResourceNotFoundException("Schema not found with id: " + schemaId);
    }
    return tableRepository.findBySchemaId(schemaId, pageable)
            .map(this::toResponse);
}

    public TableResponse getTableById(UUID id) {
        TableEntity entity = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
        return toResponse(entity);
    }

    public TableResponse updateSensitivityLevel(UUID id, SensitivityLevel level) {
        TableEntity entity = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
        entity.setSensitivityLevel(level);
        return toResponse(tableRepository.save(entity));
    }

    private TableResponse toResponse(TableEntity entity) {
        return TableResponse.builder()
                .id(entity.getId())
                .schemaId(entity.getSchema().getId())
                .schemaName(entity.getSchema().getName())
                .name(entity.getName())
                .rowCount(entity.getRowCount())
                .sensitivityLevel(entity.getSensitivityLevel())
                .scanStatus(entity.getScanStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}