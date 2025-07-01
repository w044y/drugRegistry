// ActiveSubstanceDto.java
package com.drugs.registry.dto;

import lombok.Data;

@Data
public class ActiveSubstanceDto {
    private Long substanceId;
    private String substanceName;
    private String strength;
    private String unit;
    private String concentration;
}
