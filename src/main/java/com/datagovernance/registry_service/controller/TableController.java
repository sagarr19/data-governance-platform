package com.datagovernance.registry_service.controller;

import com.datagovernance.registry_service.dto.request.CreateTableRequest;
import com.datagovernance.registry_service.dto.response.TableResponse;
import com.datagovernance.registry_service.entity.enums.SensitivityLevel;
import com.datagovernance.registry_service.service.TableService;
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
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping
    public ResponseEntity<TableResponse> createTable(
            @Valid @RequestBody CreateTableRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tableService.createTable(request));
    }

@GetMapping("/schema/{schemaId}")
public ResponseEntity<Page<TableResponse>> getTablesBySchema(
        @PathVariable UUID schemaId,
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
    return ResponseEntity.ok(tableService.getTablesBySchemaPaged(schemaId, pageable));
}

    @GetMapping("/{id}")
    public ResponseEntity<TableResponse> getTableById(@PathVariable UUID id) {
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @PatchMapping("/{id}/sensitivity")
    public ResponseEntity<TableResponse> updateSensitivityLevel(
            @PathVariable UUID id,
            @RequestParam SensitivityLevel level) {
        return ResponseEntity.ok(tableService.updateSensitivityLevel(id, level));
    }
}