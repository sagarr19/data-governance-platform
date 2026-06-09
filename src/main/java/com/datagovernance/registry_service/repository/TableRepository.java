package com.datagovernance.registry_service.repository;

import com.datagovernance.registry_service.entity.TableEntity;
import com.datagovernance.registry_service.entity.enums.ScanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.datagovernance.registry_service.entity.enums.ScanStatus;
import com.datagovernance.registry_service.entity.enums.SensitivityLevel;

import java.util.List;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, UUID> {

    List<TableEntity> findBySchemaId(UUID schemaId);

    List<TableEntity> findByScanStatus(ScanStatus scanStatus);

    boolean existsBySchemaIdAndName(UUID schemaId, String name);
    
    Page<TableEntity> findBySchemaId(UUID schemaId, Pageable pageable);
    
    long countBySchemaDataSourceId(UUID dataSourceId);

    long countBySchemaDataSourceIdAndScanStatus(UUID dataSourceId, ScanStatus scanStatus);

    long countBySchemaDataSourceIdAndSensitivityLevel(UUID dataSourceId, SensitivityLevel level);
}