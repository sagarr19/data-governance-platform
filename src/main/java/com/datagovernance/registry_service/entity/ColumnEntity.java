package com.datagovernance.registry_service.entity;

import com.datagovernance.registry_service.entity.enums.SensitivityLabel;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

@Entity
@Table(name = "columns", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"table_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private TableEntity table;

    @Column(nullable = false)
    private String name;

    @Column(name = "data_type")
    private String dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensitivity_label")
    private SensitivityLabel sensitivityLabel;

    @Column(name = "is_pii", nullable = false)
    @JsonProperty("isPii")
    private boolean pii;;
}