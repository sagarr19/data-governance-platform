package com.datagovernance.registry_service.entity;

import com.datagovernance.registry_service.entity.enums.ScanStatus;
import com.datagovernance.registry_service.entity.enums.SensitivityLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tables", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"schema_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schema_id", nullable = false)
    private SchemaEntity schema;

    @Column(nullable = false)
    private String name;

    @Column(name = "row_count")
    private Long rowCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensitivity_level")
    private SensitivityLevel sensitivityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "scan_status")
    private ScanStatus scanStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}