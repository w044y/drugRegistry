// PackagingDetail.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "packaging_details")
@Data
public class PackagingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_id")
    private Long packagingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private MedicinalProduct medicinalProduct;

    @Column(name = "ean_code", length = 20)
    private String eanCode;

    @Column(name = "prescription_type", length = 10)
    private String prescriptionType;

    @Column(name = "package_number", length = 20)
    private String packageNumber;

    @Column(name = "package_size", length = 200)
    private String packageSize;

    @Column(name = "package_description", columnDefinition = "TEXT")
    private String packageDescription;
}