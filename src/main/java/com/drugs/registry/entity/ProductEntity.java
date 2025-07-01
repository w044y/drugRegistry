// ProductEntity.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_entities")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private MedicinalProduct medicinalProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    private ResponsibleEntity responsibleEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type", nullable = false)
    private RelationshipType relationshipType;

    public enum RelationshipType {
        RESPONSIBLE, MANUFACTURER, IMPORTER, MANUFACTURER_IMPORTER, EXPORT_RESPONSIBLE
    }
}