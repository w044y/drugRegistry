// MedicinalProductRepository.java
package com.drugs.registry.repository;

import com.drugs.registry.entity.MedicinalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicinalProductRepository extends JpaRepository<MedicinalProduct, String> {

    // Find by product name (case-insensitive)
    List<MedicinalProduct> findByProductNameContainingIgnoreCase(String productName);

    // Find by common name (case-insensitive)
    List<MedicinalProduct> findByCommonNameContainingIgnoreCase(String commonName);

    // Find by ATC code
    List<MedicinalProduct> findByAtcCodeContainingIgnoreCase(String atcCode);

    // Find by active substance
    List<MedicinalProduct> findByActiveSubstanceContainingIgnoreCase(String activeSubstance);

    // Custom query to search across multiple fields
    @Query("SELECT mp FROM MedicinalProduct mp WHERE " +
            "LOWER(mp.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(mp.commonName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(mp.activeSubstance) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(mp.atcCode) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<MedicinalProduct> searchProducts(@Param("searchTerm") String searchTerm);

    // Count products
    long count();
}
