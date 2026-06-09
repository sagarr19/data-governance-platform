package com.datagovernance.registry_service.controller;

import com.datagovernance.registry_service.dto.request.CreateSchemaRequest;
import com.datagovernance.registry_service.dto.response.SchemaResponse;
import com.datagovernance.registry_service.service.SchemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schemas")
@RequiredArgsConstructor
public class SchemaController {

    private final SchemaService schemaService;

    @PostMapping
    public ResponseEntity<SchemaResponse> createSchema(
            @Valid @RequestBody CreateSchemaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(schemaService.createSchema(request));
    }

@GetMapping("/datasource/{dataSourceId}")
public ResponseEntity<Page<SchemaResponse>> getSchemasByDataSource(
        @PathVariable UUID dataSourceId,
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
    return ResponseEntity.ok(schemaService.getSchemasByDataSourcePaged(dataSourceId, pageable));
}

    @GetMapping("/{id}")
    public ResponseEntity<SchemaResponse> getSchemaById(@PathVariable UUID id) {
        return ResponseEntity.ok(schemaService.getSchemaById(id));
    }
}