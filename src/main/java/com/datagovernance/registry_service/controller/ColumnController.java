package com.datagovernance.registry_service.controller;

import com.datagovernance.registry_service.dto.request.CreateColumnRequest;
import com.datagovernance.registry_service.dto.response.ColumnResponse;
import com.datagovernance.registry_service.service.ColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<ColumnResponse> createColumn(
            @Valid @RequestBody CreateColumnRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(columnService.createColumn(request));
    }

    @GetMapping("/table/{tableId}")
    public ResponseEntity<List<ColumnResponse>> getColumnsByTable(
            @PathVariable UUID tableId) {
        return ResponseEntity.ok(columnService.getColumnsByTable(tableId));
    }

    @GetMapping("/table/{tableId}/pii")
    public ResponseEntity<List<ColumnResponse>> getPiiColumns(
            @PathVariable UUID tableId) {
        return ResponseEntity.ok(columnService.getPiiColumnsByTable(tableId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable UUID id) {
        columnService.deleteColumn(id);
        return ResponseEntity.noContent().build();
}
}