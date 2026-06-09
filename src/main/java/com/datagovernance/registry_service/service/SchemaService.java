package com.datagovernance.registry_service.service;

import com.datagovernance.registry_service.dto.request.CreateSchemaRequest;
import com.datagovernance.registry_service.dto.response.SchemaResponse;
import com.datagovernance.registry_service.entity.DataSourceEntity;
import com.datagovernance.registry_service.entity.SchemaEntity;
import com.datagovernance.registry_service.repository.DataSourceRepository;
import com.datagovernance.registry_service.repository.SchemaRepository;
import com.datagovernance.registry_service.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchemaService {

    private final SchemaRepository schemaRepository;
    private final DataSourceRepository dataSourceRepository;

    public SchemaResponse createSchema(CreateSchemaRequest request) {
        DataSourceEntity dataSource = dataSourceRepository.findById(request.getDataSourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Data source not found with id: " + request.getDataSourceId()));

        if (schemaRepository.existsByDataSourceIdAndName(request.getDataSourceId(), request.getName())) {
            throw new DuplicateResourceException("Schema '" + request.getName() + "' already exists in this data source");
        }

        SchemaEntity entity = SchemaEntity.builder()
                .dataSource(dataSource)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        SchemaEntity saved = schemaRepository.save(entity);
        return toResponse(saved);
    }

   public Page<SchemaResponse> getSchemasByDataSourcePaged(UUID dataSourceId, Pageable pageable) {
    if (!dataSourceRepository.existsById(dataSourceId)) {
        throw new ResourceNotFoundException("Data source not found with id: " + dataSourceId);
    }
    return schemaRepository.findByDataSourceId(dataSourceId, pageable)
            .map(this::toResponse);
}

    public SchemaResponse getSchemaById(UUID id) {
        SchemaEntity entity = schemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schema not found with id: " + id));
        return toResponse(entity);
    }
    

    private SchemaResponse toResponse(SchemaEntity entity) {
        return SchemaResponse.builder()
                .id(entity.getId())
                .dataSourceId(entity.getDataSource().getId())
                .dataSourceName(entity.getDataSource().getName())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}