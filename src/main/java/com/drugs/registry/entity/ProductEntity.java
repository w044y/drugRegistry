// ProductEntity.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_entities")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicinalProduct getMedicinalProduct() {
        return medicinalProduct;
    }

    public void setMedicinalProduct(MedicinalProduct medicinalProduct) {
        this.medicinalProduct = medicinalProduct;
    }

    public ResponsibleEntity getResponsibleEntity() {
        return responsibleEntity;
    }

    public void setResponsibleEntity(ResponsibleEntity responsibleEntity) {
        this.responsibleEntity = responsibleEntity;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }
}