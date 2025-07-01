// MedicinalProductDto.java
package com.drugs.registry.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MedicinalProductDto {
    private String productId;
    private String productName;
    private String commonName;
    private String preparationType;
    private Boolean animalUsageBan;
    private String previousName;
    private String administrationRoute;
    private String strength;
    private String pharmaceuticalForm;
    private String procedureType;
    private String authorizationNumber;
    private String authorizationValidity;
    private String atcCode;
    private String packaging;
    private String activeSubstance;
    private String legalBasis;
    private String leafletUrl;
    private String characteristicsUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Related entities
    private List<ResponsibleEntityDto> entities;
    private List<ActiveSubstanceDto> substances;
    private List<PackagingDetailDto> packagingDetails;
}