// ResponsibleEntity.java
package com.drugs.registry.entity;

import com.drugs.registry.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "responsible_entities")
@Data
@EqualsAndHashCode(exclude = "productEntities")
@ToString(exclude = "productEntities")
public class ResponsibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "entity_name", length = 500, nullable = false, unique = true)
    private String entityName;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "country", length = 100)
    private String country;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "responsibleEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductEntity> productEntities;

    public enum EntityType {
        RESPONSIBLE, MANUFACTURER, IMPORTER, EXPORT_RESPONSIBLE
    }
}
