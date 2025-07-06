package com.drugs.registry.service;


import com.drugs.registry.entity.*;
import com.drugs.registry.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DataImportService {

    private final MedicinalProductRepository medicinalProductRepository;
    private final ResponsibleEntityRepository responsibleEntityRepository;
    private final ProductEntityRepository productEntityRepository;
    private final ActiveSubstanceRepository activeSubstanceRepository;
    private final ProductSubstanceRepository productSubstanceRepository;
    private final PackagingDetailRepository packagingDetailRepository;

    public int importFromExcel(MultipartFile file) throws IOException {

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, List<String>> data = new HashMap<>();
            Map<Integer, String> headerNames = new HashMap<>();

            // Parse Excel data
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Header row
                    for (Cell cell : row) {
                        headerNames.put(cell.getColumnIndex(), getCellValueAsString(cell));
                    }
                } else {
                    // Data rows
                    List<String> dataByRow = new ArrayList<>();
                    for (Cell cell : row) {
                        dataByRow.add(getCellValueAsString(cell));
                    }
                    data.put(row.getRowNum(), dataByRow);
                }
            }

            // Process data and create entities
            int importedCount = 0;
            for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
                try {
                    processRow(entry.getValue());
                    importedCount++;

                    if (importedCount % 100 == 0) {
                    }
                } catch (Exception e) {
                }
            }

            return importedCount;
        }
    }

    private void processRow(List<String> rowData) {
        if (rowData.isEmpty() || isNotEmpty(rowData.get(0)) == false) {
            return; // Skip empty rows
        }

        // Create MedicinalProduct entity
        MedicinalProduct product = new MedicinalProduct();
        product.setProductId(rowData.get(0));                    // Column A
        product.setProductName(rowData.get(1));                  // Column B
        product.setCommonName(rowData.get(2));                   // Column C
        product.setPreparationType(rowData.get(3));              // Column D
        product.setAnimalUsageBan(isNotEmpty(rowData.get(4)));   // Column E
        product.setPreviousName(rowData.get(5));                 // Column F
        product.setAdministrationRoute(rowData.get(6));          // Column G
        product.setStrength(rowData.get(7));                     // Column H
        product.setPharmaceuticalForm(rowData.get(8));           // Column I
        product.setProcedureType(rowData.get(9));                // Column J
        product.setAuthorizationNumber(rowData.get(10));         // Column K
        product.setAuthorizationValidity(rowData.get(11));       // Column L
        product.setAtcCode(rowData.get(12));                     // Column M
        product.setPackaging(rowData.get(14));                   // Column O
        product.setActiveSubstance(rowData.get(15));             // Column P
        product.setLegalBasis(rowData.get(24));                  // Column Y
        product.setLeafletUrl(rowData.get(25));                  // Column Z
        product.setCharacteristicsUrl(rowData.get(26));          // Column AA
        product.setLabelingLeafletUrl(rowData.get(27));          // Column AB
        product.setParallelImportLeafletUrl(rowData.get(28));    // Column AC
        product.setParallelImportLabelingUrl(rowData.get(29));   // Column AD
        product.setParallelImportPackagingUrl(rowData.get(30));  // Column AE
        product.setEducationalMaterialsProfessionalsUrl(rowData.get(31)); // Column AF
        product.setEducationalMaterialsPatientsUrl(rowData.get(32));      // Column AG

        // Save the main product first
        MedicinalProduct savedProduct = medicinalProductRepository.save(product);

        // Process related entities
        processResponsibleEntity(savedProduct, rowData.get(13));                    // Column N
        processManufacturer(savedProduct, rowData.get(16), rowData.get(17));        // Columns Q-R
        processImporter(savedProduct, rowData.get(18), rowData.get(19));            // Columns S-T
        processManufacturerImporter(savedProduct, rowData.get(20), rowData.get(21)); // Columns U-V
        processExportResponsible(savedProduct, rowData.get(22), rowData.get(23));   // Columns W-X

        // Process substances and packaging
        processActiveSubstances(savedProduct, rowData.get(15));  // Column P
        processPackagingDetails(savedProduct, rowData.get(14));  // Column O
    }

    private void processResponsibleEntity(MedicinalProduct product, String entityName) {
        if (isNotEmpty(entityName)) {
            ResponsibleEntity entity = findOrCreateEntity(entityName, ResponsibleEntity.EntityType.RESPONSIBLE, null);
            createProductEntityRelation(product, entity, ProductEntity.RelationshipType.RESPONSIBLE);
        }
    }

    private void processManufacturer(MedicinalProduct product, String manufacturerName, String country) {
        if (isNotEmpty(manufacturerName)) {
            ResponsibleEntity entity = findOrCreateEntity(manufacturerName, ResponsibleEntity.EntityType.MANUFACTURER, country);
            createProductEntityRelation(product, entity, ProductEntity.RelationshipType.MANUFACTURER);
        }
    }

    private void processImporter(MedicinalProduct product, String importerName, String country) {
        if (isNotEmpty(importerName)) {
            ResponsibleEntity entity = findOrCreateEntity(importerName, ResponsibleEntity.EntityType.IMPORTER, country);
            createProductEntityRelation(product, entity, ProductEntity.RelationshipType.IMPORTER);
        }
    }

    private void processManufacturerImporter(MedicinalProduct product, String entityName, String country) {
        if (isNotEmpty(entityName)) {
            ResponsibleEntity entity = findOrCreateEntity(entityName, ResponsibleEntity.EntityType.MANUFACTURER, country);
            createProductEntityRelation(product, entity, ProductEntity.RelationshipType.MANUFACTURER_IMPORTER);
        }
    }

    private void processExportResponsible(MedicinalProduct product, String entityName, String country) {
        if (isNotEmpty(entityName)) {
            ResponsibleEntity entity = findOrCreateEntity(entityName, ResponsibleEntity.EntityType.EXPORT_RESPONSIBLE, country);
            createProductEntityRelation(product, entity, ProductEntity.RelationshipType.EXPORT_RESPONSIBLE);
        }
    }

    private ResponsibleEntity findOrCreateEntity(String entityName, ResponsibleEntity.EntityType type, String country) {
        return responsibleEntityRepository.findByEntityNameIgnoreCase(entityName)
                .orElseGet(() -> {
                    ResponsibleEntity entity = new ResponsibleEntity();
                    entity.setEntityName(entityName);
                    entity.setEntityType(type);
                    entity.setCountry(country);
                    return responsibleEntityRepository.save(entity);
                });
    }

    private void createProductEntityRelation(MedicinalProduct product, ResponsibleEntity entity,
                                             ProductEntity.RelationshipType relationshipType) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMedicinalProduct(product);
        productEntity.setResponsibleEntity(entity);
        productEntity.setRelationshipType(relationshipType);
        productEntityRepository.save(productEntity);
    }

    private void processActiveSubstances(MedicinalProduct product, String substanceData) {
        if (!isNotEmpty(substanceData)) return;

        // Parse substance data format: "Acidum zoledronicum 4 mg/5 ml"
        String[] substances = substanceData.split("[,;]");

        for (String substanceInfo : substances) {
            substanceInfo = substanceInfo.trim();
            if (substanceInfo.isEmpty()) continue;

            // Extract substance name and concentration
            Pattern pattern = Pattern.compile("(.+?)\\s+(\\d+(?:\\.\\d+)?\\s*(?:mg|g|ml|%|IU|U).*)");
            Matcher matcher = pattern.matcher(substanceInfo);

            String substanceName;
            String concentration = null;

            if (matcher.find()) {
                substanceName = matcher.group(1).trim();
                concentration = matcher.group(2).trim();
            } else {
                substanceName = substanceInfo;
            }

            ActiveSubstance substance = findOrCreateSubstance(substanceName);

            ProductSubstance productSubstance = new ProductSubstance();
            productSubstance.setMedicinalProduct(product);
            productSubstance.setActiveSubstance(substance);
            productSubstance.setConcentration(concentration);

            productSubstanceRepository.save(productSubstance);
        }
    }

    private ActiveSubstance findOrCreateSubstance(String substanceName) {
        return activeSubstanceRepository.findBySubstanceNameIgnoreCase(substanceName)
                .orElseGet(() -> {
                    ActiveSubstance substance = new ActiveSubstance();
                    substance.setSubstanceName(substanceName);
                    return activeSubstanceRepository.save(substance);
                });
    }

    private void processPackagingDetails(MedicinalProduct product, String packagingData) {
        if (!isNotEmpty(packagingData)) return;

        // Parse packaging data format: "05909991023652 ¦ Rpz ¦ 2\n1 fiol. 5 ml"
        String[] lines = packagingData.split("\\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            PackagingDetail detail = new PackagingDetail();
            detail.setMedicinalProduct(product);

            // Parse EAN code, prescription type, and package info
            String[] parts = line.split("¦");
            if (parts.length >= 3) {
                detail.setEanCode(parts[0].trim());
                detail.setPrescriptionType(parts[1].trim());
                detail.setPackageNumber(parts[2].trim());
            }

            // Try to extract package size from the line
            Pattern sizePattern = Pattern.compile("(\\d+(?:\\.\\d+)?\\s*(?:ml|g|mg|tabl\\.|fiol\\.|amp\\.|szt\\.)?)");
            Matcher matcher = sizePattern.matcher(line);
            if (matcher.find()) {
                detail.setPackageSize(matcher.group(1));
            }

            detail.setPackageDescription(line);
            packagingDetailRepository.save(detail);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}