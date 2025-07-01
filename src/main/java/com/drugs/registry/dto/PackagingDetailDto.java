// PackagingDetailDto.java
package com.drugs.registry.dto;

import lombok.Data;

@Data
public class PackagingDetailDto {
    private Long packagingId;
    private String eanCode;
    private String prescriptionType;
    private String packageNumber;
    private String packageSize;
    private String packageDescription;
}
