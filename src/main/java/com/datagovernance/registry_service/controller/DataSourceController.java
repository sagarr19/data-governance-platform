package com.datagovernance.registry_service.controller;

import com.datagovernance.registry_service.dto.request.CreateDataSourceRequest;
import com.datagovernance.registry_service.dto.response.DataSourceResponse;
import com.datagovernance.registry_service.dto.response.DataSourceSummaryResponse;
import com.datagovernance.registry_service.service.DataSourceService;
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
@RequestMapping("/api/v1/datasources")
@RequiredArgsConstructor
public class DataSourceController {

    private final DataSourceService dataSourceService;

    @PostMapping
    public ResponseEntity<DataSourceResponse> createDataSource(
            @Valid @RequestBody CreateDataSourceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dataSourceService.createDataSource(request));
    }
    
@GetMapping
public ResponseEntity<Page<DataSourceResponse>> getAllDataSources(
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
    return ResponseEntity.ok(dataSourceService.getAllDataSourcesPaged(pageable));
}

    @GetMapping("/{id}")
    public ResponseEntity<DataSourceResponse> getDataSourceById(@PathVariable UUID id) {
        return ResponseEntity.ok(dataSourceService.getDataSourceById(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateDataSource(@PathVariable UUID id) {
        dataSourceService.deactivateDataSource(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateDataSource(@PathVariable UUID id) {
    dataSourceService.activateDataSource(id);
    return ResponseEntity.noContent().build();
}
    @GetMapping("/{id}/summary")
    public ResponseEntity<DataSourceSummaryResponse> getDataSourceSummary(@PathVariable UUID id) {
    return ResponseEntity.ok(dataSourceService.getDataSourceSummary(id));
}
}