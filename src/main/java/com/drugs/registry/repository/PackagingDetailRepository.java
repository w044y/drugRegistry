package com.drugs.registry.repository;

import com.drugs.registry.entity.PackagingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackagingDetailRepository extends JpaRepository<PackagingDetail, Long> {

    // Find all packaging details for a specific product
    List<PackagingDetail> findByMedicinalProductProductId(String productId);

    // Find by EAN code
    Optional<PackagingDetail> findByEanCode(String eanCode);

    // Find by prescription type
    List<PackagingDetail> findByPrescriptionType(String prescriptionType);

    // Find by package number
    List<PackagingDetail> findByPackageNumber(String packageNumber);

    // Find by package size
    List<PackagingDetail> findByPackageSize(String packageSize);

    // Find by prescription type and package size
    List<PackagingDetail> findByPrescriptionTypeAndPackageSize(String prescriptionType, String packageSize);

    // Check if EAN code exists
    boolean existsByEanCode(String eanCode);

    // Get prescription type statistics
    @Query("SELECT pd.prescriptionType, COUNT(pd) FROM PackagingDetail pd " +
            "WHERE pd.prescriptionType IS NOT NULL " +
            "GROUP BY pd.prescriptionType " +
            "ORDER BY COUNT(pd) DESC")
    List<Object[]> findPrescriptionTypeStatistics();

    // Get package size statistics
    @Query("SELECT pd.packageSize, COUNT(pd) FROM PackagingDetail pd " +
            "WHERE pd.packageSize IS NOT NULL " +
            "GROUP BY pd.packageSize " +
            "ORDER BY COUNT(pd) DESC")
    List<Object[]> findPackageSizeStatistics();

    // Find products with multiple package variants
    @Query("SELECT mp.productId, COUNT(pd) FROM PackagingDetail pd " +
            "JOIN pd.medicinalProduct mp " +
            "GROUP BY mp.productId " +
            "HAVING COUNT(pd) > 1 " +
            "ORDER BY COUNT(pd) DESC")
    List<Object[]> findProductsWithMultiplePackages();

    // Find packaging by description containing text
    List<PackagingDetail> findByPackageDescriptionContainingIgnoreCase(String description);
}