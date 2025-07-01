// SearchCriteriaDto.java
package com.drugs.registry.dto;

import lombok.Data;

@Data
public class SearchCriteriaDto {
    private String productName;
    private String commonName;
    private String preparationType;
    private String atcCode;
    private String authorizationNumber;
    private String entityName;
    private String activeSubstance;
    private Boolean animalUsageBan;
    private int page = 0;
    private int size = 20;
    private String sortBy = "productName";
    private String sortDirection = "ASC";
}