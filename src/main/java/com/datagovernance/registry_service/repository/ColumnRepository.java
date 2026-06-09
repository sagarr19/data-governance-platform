package com.datagovernance.registry_service.repository;

import com.datagovernance.registry_service.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, UUID> {

    List<ColumnEntity> findByTableId(UUID tableId);

    @Query("SELECT c FROM ColumnEntity c WHERE c.table.id = :tableId AND c.pii = true")
    List<ColumnEntity> findPiiColumnsByTableId(@Param("tableId") UUID tableId);

    boolean existsByTableIdAndName(UUID tableId, String name);
    
    long countByTableSchemaDataSourceId(UUID dataSourceId);

    long countByTableSchemaDataSourceIdAndPiiTrue(UUID dataSourceId);
}

