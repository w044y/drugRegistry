// MedicinalProductRepository.java
package com.drugs.registry.repository;

import com.drugs.registry.entity.MedicinalProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MedicinalProductRepository extends JpaRepository<MedicinalProduct, Long>,
        JpaSpecificationExecutor<MedicinalProduct> {


    Page<MedicinalProduct> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    Page<MedicinalProduct> findByCommonNameContainingIgnoreCase(String commonName, Pageable pageable);

    Page<MedicinalProduct> findByPreparationType(String preparationType, Pageable pageable);

    Page<MedicinalProduct> findByAtcCodeStartingWith(String atcCode, Pageable pageable);

    Optional<MedicinalProduct> findByAuthorizationNumber(String authorizationNumber);

    @Query("SELECT mp FROM MedicinalProduct mp " +
            "JOIN mp.productEntities pe " +
            "JOIN pe.responsibleEntity re " +
            "WHERE re.entityName LIKE %:entityName%")
    Page<MedicinalProduct> findByEntityName(@Param("entityName") String entityName, Pageable pageable);

    @Query("SELECT mp FROM MedicinalProduct mp " +
            "WHERE mp.activeSubstance LIKE %:substance%")
    Page<MedicinalProduct> findByActiveSubstanceContaining(@Param("substance") String substance, Pageable pageable);

    @Query("SELECT COUNT(mp) FROM MedicinalProduct mp WHERE mp.preparationType = :type")
    long countByPreparationType(@Param("type") String type);

    @Query("SELECT mp.atcCode, COUNT(mp) FROM MedicinalProduct mp " +
            "WHERE mp.atcCode IS NOT NULL " +
            "GROUP BY mp.atcCode " +
            "ORDER BY COUNT(mp) DESC")
    List<Object[]> findAtcCodeStatistics();
}