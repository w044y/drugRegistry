// ProductSubstance.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_substances")
@Data
public class ProductSubstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private MedicinalProduct medicinalProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substance_id")
    private ActiveSubstance activeSubstance;

    @Column(name = "concentration", length = 200)
    private String concentration;
}