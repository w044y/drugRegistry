// MedicinalProduct.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "medicinal_products")
@Data
@EqualsAndHashCode(exclude = {"productEntities", "productSubstances", "packagingDetails"})
@ToString(exclude = {"productEntities", "productSubstances", "packagingDetails"})
public class MedicinalProduct {

    @Id
    @Column(name = "product_id", length = 20)
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

    // Relationships
    @OneToMany(mappedBy = "medicinalProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductEntity> productEntities;

    @OneToMany(mappedBy = "medicinalProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductSubstance> productSubstances;

    @OneToMany(mappedBy = "medicinalProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PackagingDetail> packagingDetails;
}