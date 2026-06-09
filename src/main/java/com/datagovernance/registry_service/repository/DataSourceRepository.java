package com.datagovernance.registry_service.repository;

import com.datagovernance.registry_service.entity.DataSourceEntity;
import com.datagovernance.registry_service.entity.enums.DataSourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSourceEntity, UUID> {

    Optional<DataSourceEntity> findByName(String name);

    List<DataSourceEntity> findByStatus(DataSourceStatus status);

    boolean existsByName(String name);
}