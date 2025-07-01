// ResponsibleEntityDto.java
package com.drugs.registry.dto;

import com.drugs.registry.entity.ResponsibleEntity;
import lombok.Data;

@Data
public class ResponsibleEntityDto {
    private Long entityId;
    private String entityName;
    private ResponsibleEntity.EntityType entityType;
    private String country;
    private String relationshipType;
}