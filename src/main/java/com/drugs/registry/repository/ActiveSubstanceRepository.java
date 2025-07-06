package com.drugs.registry.repository;

import com.drugs.registry.entity.ActiveSubstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActiveSubstanceRepository extends JpaRepository<ActiveSubstance, Long> {

    // Find substance by name (case insensitive)
    Optional<ActiveSubstance> findBySubstanceNameIgnoreCase(String substanceName);

    // Find substances by name containing text (case insensitive)
    Page<ActiveSubstance> findBySubstanceNameContainingIgnoreCase(String substanceName, Pageable pageable);

    // Find substances by strength
    List<ActiveSubstance> findByStrength(String strength);

    // Find substances by unit
    List<ActiveSubstance> findByUnit(String unit);

    // Check if substance exists by name
    boolean existsBySubstanceNameIgnoreCase(String substanceName);

    // Find most used substances (ordered by usage count)
    @Query("SELECT as FROM ActiveSubstance as " +
            "JOIN as.productSubstances ps " +
            "GROUP BY as " +
            "ORDER BY COUNT(ps) DESC")
    List<ActiveSubstance> findMostUsedSubstances(Pageable pageable);

    // Get substance usage statistics
    @Query("SELECT as.substanceName, COUNT(ps) FROM ActiveSubstance as " +
            "JOIN as.productSubstances ps " +
            "GROUP BY as.substanceName " +
            "ORDER BY COUNT(ps) DESC")
    List<Object[]> findSubstanceUsageStatistics();

    // Find substances used in specific preparation type
    @Query("SELECT DISTINCT as FROM ActiveSubstance as " +
            "JOIN as.productSubstances ps " +
            "JOIN ps.medicinalProduct mp " +
            "WHERE mp.preparationType = :preparationType")
    List<ActiveSubstance> findByPreparationType(String preparationType);
}