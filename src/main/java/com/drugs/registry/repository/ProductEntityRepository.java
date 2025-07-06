package com.drugs.registry.repository;

import com.drugs.registry.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    // Find all product-entity relationships for a specific product
    List<ProductEntity> findByMedicinalProductProductId(String productId);

    // Find all products for a specific entity
    List<ProductEntity> findByResponsibleEntityEntityId(Long entityId);

    // Find by relationship type
    List<ProductEntity> findByRelationshipType(ProductEntity.RelationshipType relationshipType);

    // Find by product and relationship type
    List<ProductEntity> findByMedicinalProductProductIdAndRelationshipType(
            String productId,
            ProductEntity.RelationshipType relationshipType
    );

    // Find by entity and relationship type
    List<ProductEntity> findByResponsibleEntityEntityIdAndRelationshipType(
            Long entityId,
            ProductEntity.RelationshipType relationshipType
    );

    // Check if relationship exists
    boolean existsByMedicinalProductProductIdAndResponsibleEntityEntityIdAndRelationshipType(
            String productId,
            Long entityId,
            ProductEntity.RelationshipType relationshipType
    );

    // Get statistics by relationship type
    @Query("SELECT pe.relationshipType, COUNT(pe) FROM ProductEntity pe " +
            "GROUP BY pe.relationshipType " +
            "ORDER BY COUNT(pe) DESC")
    List<Object[]> findRelationshipTypeStatistics();
}