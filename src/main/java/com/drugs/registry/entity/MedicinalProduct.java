// MedicinalProduct.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "medicinal_products")
public class MedicinalProduct {

    @Id
    @Column(name = "product_id", length = 50)
    private String productId;

    @Column(name = "product_name", length = 500, nullable = false)
    private String productName;

    @Column(name = "common_name", length = 500)
    private String commonName;

    @Column(name = "preparation_type", length = 100)
    private String preparationType;

    @Column(name = "animal_usage_ban")
    private Boolean animalUsageBan = false;

    @Column(name = "previous_name", length = 500)
    private String previousName;

    @Column(name = "administration_route", columnDefinition = "TEXT")
    private String administrationRoute;

    @Column(name = "strength", length = 200)
    private String strength;

    @Column(name = "pharmaceutical_form", length = 200)
    private String pharmaceuticalForm;

    @Column(name = "procedure_type", length = 50)
    private String procedureType;

    @Column(name = "authorization_number", length = 50)
    private String authorizationNumber;

    @Column(name = "authorization_validity", length = 50)
    private String authorizationValidity;

    @Column(name = "atc_code", length = 20)
    private String atcCode;

    @Column(name = "packaging", columnDefinition = "TEXT")
    private String packaging;

    @Column(name = "active_substance", columnDefinition = "TEXT")
    private String activeSubstance;

    @Column(name = "legal_basis", length = 200)
    private String legalBasis;

    @Column(name = "leaflet_url", length = 500)
    private String leafletUrl;

    @Column(name = "characteristics_url", length = 500)
    private String characteristicsUrl;

    @Column(name = "labeling_leaflet_url", length = 500)
    private String labelingLeafletUrl;

    @Column(name = "parallel_import_leaflet_url", length = 500)
    private String parallelImportLeafletUrl;

    @Column(name = "parallel_import_labeling_url", length = 500)
    private String parallelImportLabelingUrl;

    @Column(name = "parallel_import_packaging_url", length = 500)
    private String parallelImportPackagingUrl;

    @Column(name = "educational_materials_professionals_url", length = 500)
    private String educationalMaterialsProfessionalsUrl;

    @Column(name = "educational_materials_patients_url", length = 500)
    private String educationalMaterialsPatientsUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships - Initialize with empty sets to avoid null pointer exceptions
    @OneToMany(mappedBy = "medicinalProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductEntity> productEntities = new HashSet<>();

    @OneToMany(mappedBy = "medicinalProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductSubstance> productSubstances = new HashSet<>();

    @OneToMany(mappedBy = "medicinalProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PackagingDetail> packagingDetails = new HashSet<>();

    // Default constructor (required by JPA)
    public MedicinalProduct() {}

    // Constructor with required fields
    public MedicinalProduct(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getPreparationType() {
        return preparationType;
    }

    public void setPreparationType(String preparationType) {
        this.preparationType = preparationType;
    }

    public Boolean getAnimalUsageBan() {
        return animalUsageBan;
    }

    public void setAnimalUsageBan(Boolean animalUsageBan) {
        this.animalUsageBan = animalUsageBan;
    }

    public String getPreviousName() {
        return previousName;
    }

    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    public String getAdministrationRoute() {
        return administrationRoute;
    }

    public void setAdministrationRoute(String administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public void setPharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public String getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public String getAuthorizationValidity() {
        return authorizationValidity;
    }

    public void setAuthorizationValidity(String authorizationValidity) {
        this.authorizationValidity = authorizationValidity;
    }

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getActiveSubstance() {
        return activeSubstance;
    }

    public void setActiveSubstance(String activeSubstance) {
        this.activeSubstance = activeSubstance;
    }

    public String getLegalBasis() {
        return legalBasis;
    }

    public void setLegalBasis(String legalBasis) {
        this.legalBasis = legalBasis;
    }

    public String getLeafletUrl() {
        return leafletUrl;
    }

    public void setLeafletUrl(String leafletUrl) {
        this.leafletUrl = leafletUrl;
    }

    public String getCharacteristicsUrl() {
        return characteristicsUrl;
    }

    public void setCharacteristicsUrl(String characteristicsUrl) {
        this.characteristicsUrl = characteristicsUrl;
    }

    public String getLabelingLeafletUrl() {
        return labelingLeafletUrl;
    }

    public void setLabelingLeafletUrl(String labelingLeafletUrl) {
        this.labelingLeafletUrl = labelingLeafletUrl;
    }

    public String getParallelImportLeafletUrl() {
        return parallelImportLeafletUrl;
    }

    public void setParallelImportLeafletUrl(String parallelImportLeafletUrl) {
        this.parallelImportLeafletUrl = parallelImportLeafletUrl;
    }

    public String getParallelImportLabelingUrl() {
        return parallelImportLabelingUrl;
    }

    public void setParallelImportLabelingUrl(String parallelImportLabelingUrl) {
        this.parallelImportLabelingUrl = parallelImportLabelingUrl;
    }

    public String getParallelImportPackagingUrl() {
        return parallelImportPackagingUrl;
    }

    public void setParallelImportPackagingUrl(String parallelImportPackagingUrl) {
        this.parallelImportPackagingUrl = parallelImportPackagingUrl;
    }

    public String getEducationalMaterialsProfessionalsUrl() {
        return educationalMaterialsProfessionalsUrl;
    }

    public void setEducationalMaterialsProfessionalsUrl(String educationalMaterialsProfessionalsUrl) {
        this.educationalMaterialsProfessionalsUrl = educationalMaterialsProfessionalsUrl;
    }

    public String getEducationalMaterialsPatientsUrl() {
        return educationalMaterialsPatientsUrl;
    }

    public void setEducationalMaterialsPatientsUrl(String educationalMaterialsPatientsUrl) {
        this.educationalMaterialsPatientsUrl = educationalMaterialsPatientsUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Set<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    public Set<ProductSubstance> getProductSubstances() {
        return productSubstances;
    }

    public void setProductSubstances(Set<ProductSubstance> productSubstances) {
        this.productSubstances = productSubstances;
    }

    public Set<PackagingDetail> getPackagingDetails() {
        return packagingDetails;
    }

    public void setPackagingDetails(Set<PackagingDetail> packagingDetails) {
        this.packagingDetails = packagingDetails;
    }

    // Helper methods for relationships
    public void addProductEntity(ProductEntity productEntity) {
        productEntities.add(productEntity);
        productEntity.setMedicinalProduct(this);
    }

    public void removeProductEntity(ProductEntity productEntity) {
        productEntities.remove(productEntity);
        productEntity.setMedicinalProduct(null);
    }

    public void addProductSubstance(ProductSubstance productSubstance) {
        productSubstances.add(productSubstance);
        productSubstance.setMedicinalProduct(this);
    }

    public void removeProductSubstance(ProductSubstance productSubstance) {
        productSubstances.remove(productSubstance);
        productSubstance.setMedicinalProduct(null);
    }

    public void addPackagingDetail(PackagingDetail packagingDetail) {
        packagingDetails.add(packagingDetail);
        packagingDetail.setMedicinalProduct(this);
    }

    public void removePackagingDetail(PackagingDetail packagingDetail) {
        packagingDetails.remove(packagingDetail);
        packagingDetail.setMedicinalProduct(null);
    }

    // equals and hashCode based on ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicinalProduct that = (MedicinalProduct) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "MedicinalProduct{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", commonName='" + commonName + '\'' +
                '}';
    }
}