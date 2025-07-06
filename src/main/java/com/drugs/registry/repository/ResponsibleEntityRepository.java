package com.drugs.registry.repository;

import com.drugs.registry.entity.ResponsibleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsibleEntityRepository extends JpaRepository<ResponsibleEntity, Long> {

    // Find entity by name (case insensitive)
    Optional<ResponsibleEntity> findByEntityNameIgnoreCase(String entityName);

    // Find entities by name containing text (case insensitive)
    Page<ResponsibleEntity> findByEntityNameContainingIgnoreCase(String entityName, Pageable pageable);

    // Find entities by type
    List<ResponsibleEntity> findByEntityType(ResponsibleEntity.EntityType entityType);

    // Find entities by country (case insensitive)
    List<ResponsibleEntity> findByCountryIgnoreCase(String country);

    // Get country statistics
    @Query("SELECT re.country, COUNT(re) FROM ResponsibleEntity re " +
            "WHERE re.country IS NOT NULL " +
            "GROUP BY re.country " +
            "ORDER BY COUNT(re) DESC")
    List<Object[]> findCountryStatistics();

    // Find entities by type and country
    List<ResponsibleEntity> findByEntityTypeAndCountryIgnoreCase(
            ResponsibleEntity.EntityType entityType,
            String country
    );

    // Check if entity exists by name
    boolean existsByEntityNameIgnoreCase(String entityName);
}