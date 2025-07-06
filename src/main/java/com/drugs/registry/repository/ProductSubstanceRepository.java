package com.drugs.registry.repository;

import com.drugs.registry.entity.ProductSubstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSubstanceRepository extends JpaRepository<ProductSubstance, Long> {

    // Find all substances for a specific product
    List<ProductSubstance> findByMedicinalProductProductId(String productId);

    // Find all products containing a specific substance
    List<ProductSubstance> findByActiveSubstanceSubstanceId(Long substanceId);

    // Find by product and substance
    List<ProductSubstance> findByMedicinalProductProductIdAndActiveSubstanceSubstanceId(
            String productId,
            Long substanceId
    );

    // Find by concentration
    List<ProductSubstance> findByConcentration(String concentration);

    // Find products with specific substance name
    @Query("SELECT ps FROM ProductSubstance ps " +
            "JOIN ps.activeSubstance as " +
            "WHERE as.substanceName LIKE %:substanceName%")
    List<ProductSubstance> findByActiveSubstanceNameContaining(@Param("substanceName") String substanceName);

    // Check if product-substance relationship exists
    boolean existsByMedicinalProductProductIdAndActiveSubstanceSubstanceId(
            String productId,
            Long substanceId
    );

    // Get concentration statistics
    @Query("SELECT ps.concentration, COUNT(ps) FROM ProductSubstance ps " +
            "WHERE ps.concentration IS NOT NULL " +
            "GROUP BY ps.concentration " +
            "ORDER BY COUNT(ps) DESC")
    List<Object[]> findConcentrationStatistics();

    // Count substances per product
    @Query("SELECT mp.productId, COUNT(ps) FROM ProductSubstance ps " +
            "JOIN ps.medicinalProduct mp " +
            "GROUP BY mp.productId " +
            "ORDER BY COUNT(ps) DESC")
    List<Object[]> countSubstancesPerProduct();
}