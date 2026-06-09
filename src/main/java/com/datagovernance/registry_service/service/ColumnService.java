package com.datagovernance.registry_service.service;

import com.datagovernance.registry_service.dto.request.CreateColumnRequest;
import com.datagovernance.registry_service.dto.response.ColumnResponse;
import com.datagovernance.registry_service.entity.ColumnEntity;
import com.datagovernance.registry_service.entity.TableEntity;
import com.datagovernance.registry_service.entity.enums.SensitivityLabel;
import com.datagovernance.registry_service.repository.ColumnRepository;
import com.datagovernance.registry_service.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import com.datagovernance.registry_service.exceptions.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final TableRepository tableRepository;

    public ColumnResponse createColumn(CreateColumnRequest request) {
        TableEntity table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + request.getTableId()));

        if (columnRepository.existsByTableIdAndName(request.getTableId(), request.getName())) {
            throw new DuplicateResourceException("Column '" + request.getName() + "' already exists in this table");
        }

        ColumnEntity entity = ColumnEntity.builder()
                .table(table)
                .name(request.getName())
                .dataType(request.getDataType())
                .sensitivityLabel(
                    request.getSensitivityLabel() != null
                        ? request.getSensitivityLabel()
                        : SensitivityLabel.NONE)
                .pii(request.isPii())
                .build();

        ColumnEntity saved = columnRepository.save(entity);
        return toResponse(saved);
    }
    
    public void deleteColumn(UUID id) {
    if (!columnRepository.existsById(id)) {
        throw new ResourceNotFoundException("Column not found with id: " + id);
    }
    columnRepository.deleteById(id);
}

    public List<ColumnResponse> getColumnsByTable(UUID tableId) {
        if (!tableRepository.existsById(tableId)) {
            throw new ResourceNotFoundException("Table not found with id: " + tableId);
        }
        return columnRepository.findByTableId(tableId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ColumnResponse> getPiiColumnsByTable(UUID tableId) {
        return columnRepository.findPiiColumnsByTableId(tableId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ColumnResponse toResponse(ColumnEntity entity) {
        return ColumnResponse.builder()
                .id(entity.getId())
                .tableId(entity.getTable().getId())
                .tableName(entity.getTable().getName())
                .name(entity.getName())
                .dataType(entity.getDataType())
                .sensitivityLabel(entity.getSensitivityLabel())
                .isPii(entity.isPii())
                .build();
    }
}