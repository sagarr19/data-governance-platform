package com.datagovernance.registry_service.entity;

import com.datagovernance.registry_service.entity.enums.DataSourceStatus;
import com.datagovernance.registry_service.entity.enums.DataSourceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "data_sources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataSourceType type;

    private String description;

    @Column(name = "connection_host")
    private String connectionHost;

    @Column(name = "connection_port")
    private Integer connectionPort;

    @Column(name = "database_name")
    private String databaseName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataSourceStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}