package com.datagovernance.registry_service.repository;

import com.datagovernance.registry_service.entity.SchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchemaRepository extends JpaRepository<SchemaEntity, UUID> {

    List<SchemaEntity> findByDataSourceId(UUID dataSourceId);
    Page<SchemaEntity> findByDataSourceId(UUID dataSourceId, Pageable pageable);
    boolean existsByDataSourceIdAndName(UUID dataSourceId, String name);
    long countByDataSourceId(UUID dataSourceId);
}

