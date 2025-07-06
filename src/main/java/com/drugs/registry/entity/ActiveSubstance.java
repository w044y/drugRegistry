// ActiveSubstance.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "active_substances")
@EqualsAndHashCode(exclude = "productSubstances")
@ToString(exclude = "productSubstances")
public class ActiveSubstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substance_id")
    private Long substanceId;

    @Column(name = "substance_name", length = 500, nullable = false)
    private String substanceName;

    @Column(name = "strength", length = 200)
    private String strength;

    @Column(name = "unit", length = 50)
    private String unit;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "activeSubstance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductSubstance> productSubstances;

    public Long getSubstanceId() {
        return substanceId;
    }

    public void setSubstanceId(Long substanceId) {
        this.substanceId = substanceId;
    }

    public String getSubstanceName() {
        return substanceName;
    }

    public void setSubstanceName(String substanceName) {
        this.substanceName = substanceName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<ProductSubstance> getProductSubstances() {
        return productSubstances;
    }

    public void setProductSubstances(Set<ProductSubstance> productSubstances) {
        this.productSubstances = productSubstances;
    }
}