// MedicinalProduct.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "medicinal_products")
public class MedicinalProduct {

    @Id
    @Column(name = "product_id", columnDefinition = "TEXT")
    private String productId;

    @Column(name = "product_name", columnDefinition = "TEXT", nullable = false)
    private String productName;

    @Column(name = "common_name", columnDefinition = "TEXT")
    private String commonName;

    @Column(name = "preparation_type", columnDefinition = "TEXT")
    private String preparationType;

    @Column(name = "animal_usage_ban")
    private Boolean animalUsageBan = false;

    @Column(name = "previous_name", columnDefinition = "TEXT")
    private String previousName;

    @Column(name = "administration_route", columnDefinition = "TEXT")
    private String administrationRoute;

    @Column(name = "strength", columnDefinition = "TEXT")
    private String strength;

    @Column(name = "pharmaceutical_form", columnDefinition = "TEXT")
    private String pharmaceuticalForm;

    @Column(name = "procedure_type", columnDefinition = "TEXT")
    private String procedureType;

    @Column(name = "authorization_number", columnDefinition = "TEXT")
    private String authorizationNumber;

    @Column(name = "authorization_validity", columnDefinition = "TEXT")
    private String authorizationValidity;

    @Column(name = "atc_code", columnDefinition = "TEXT")
    private String atcCode;

    @Column(name = "packaging", columnDefinition = "TEXT")
    private String packaging;

    @Column(name = "active_substance", columnDefinition = "TEXT")
    private String activeSubstance;

    @Column(name = "legal_basis", columnDefinition = "TEXT")
    private String legalBasis;

    @Column(name = "leaflet_url", columnDefinition = "TEXT")
    private String leafletUrl;

    @Column(name = "characteristics_url", columnDefinition = "TEXT")
    private String characteristicsUrl;

    @Column(name = "labeling_leaflet_url", columnDefinition = "TEXT")
    private String labelingLeafletUrl;

    @Column(name = "parallel_import_leaflet_url", columnDefinition = "TEXT")
    private String parallelImportLeafletUrl;

    @Column(name = "parallel_import_labeling_url", columnDefinition = "TEXT")
    private String parallelImportLabelingUrl;

    @Column(name = "parallel_import_packaging_url", columnDefinition = "TEXT")
    private String parallelImportPackagingUrl;

    @Column(name = "educational_materials_professionals_url", columnDefinition = "TEXT")
    private String educationalMaterialsProfessionalsUrl;

    @Column(name = "educational_materials_patients_url", columnDefinition = "TEXT")
    private String educationalMaterialsPatientsUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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