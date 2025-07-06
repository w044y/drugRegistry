// PackagingDetail.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "packaging_details")
public class PackagingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_id")
    private Long packagingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private MedicinalProduct medicinalProduct;

    @Column(name = "ean_code", length = 50)
    private String eanCode;

    @Column(name = "prescription_type", length = 50)
    private String prescriptionType;

    @Column(name = "package_number", length = 50)
    private String packageNumber;

    @Column(name = "package_size", length = 200)
    private String packageSize;

    @Column(name = "package_description", columnDefinition = "TEXT")
    private String packageDescription;

    public Long getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(Long packagingId) {
        this.packagingId = packagingId;
    }

    public MedicinalProduct getMedicinalProduct() {
        return medicinalProduct;
    }

    public void setMedicinalProduct(MedicinalProduct medicinalProduct) {
        this.medicinalProduct = medicinalProduct;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }
}