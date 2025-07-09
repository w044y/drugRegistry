package com.drugs.registry.service;

import com.drugs.registry.entity.MedicinalProduct;
import com.drugs.registry.repository.MedicinalProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataImportService {

    private final MedicinalProductRepository medicinalProductRepository;
    private static final int BATCH_SIZE = 50; // Process in smaller batches

    public int importFromExcel(MultipartFile file) throws IOException {
        log.info("Starting Excel import from file: {}", file.getOriginalFilename());

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int totalRows = sheet.getLastRowNum();
            log.info("Found {} data rows to process", totalRows);

            Map<Integer, List<String>> data = new HashMap<>();
            Map<Integer, String> headerNames = new HashMap<>();

            // Parse Excel data
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Header row - log for debugging
                    for (Cell cell : row) {
                        String headerValue = getCellValueAsString(cell);
                        headerNames.put(cell.getColumnIndex(), headerValue);
                        log.debug("Column {}: {}", cell.getColumnIndex(), headerValue);
                    }
                } else {
                    // Data rows - ensure we have enough columns
                    List<String> dataByRow = new ArrayList<>();
                    int maxColumn = Math.max(32, row.getLastCellNum()); // Ensure we have at least 33 columns (0-32)

                    for (int i = 0; i < maxColumn; i++) {
                        Cell cell = row.getCell(i);
                        dataByRow.add(getCellValueAsString(cell));
                    }
                    data.put(row.getRowNum(), dataByRow);
                }
            }

            // Process data in batches
            int importedCount = 0;
            int skippedCount = 0;
            int errorCount = 0;

            List<Map.Entry<Integer, List<String>>> entries = new ArrayList<>(data.entrySet());

            // Process in batches to avoid large transaction failures
            for (int i = 0; i < entries.size(); i += BATCH_SIZE) {
                int endIndex = Math.min(i + BATCH_SIZE, entries.size());
                List<Map.Entry<Integer, List<String>>> batch = entries.subList(i, endIndex);

                log.info("Processing batch {}-{} of {}", i + 1, endIndex, entries.size());

                BatchResult batchResult = processBatch(batch);
                importedCount += batchResult.imported;
                skippedCount += batchResult.skipped;
                errorCount += batchResult.errors;

                if (importedCount % 100 == 0 && importedCount > 0) {
                    log.info("Total processed so far - Imported: {}, Skipped: {}, Errors: {}",
                            importedCount, skippedCount, errorCount);
                }
            }

            log.info("Import completed - Imported: {}, Skipped: {}, Errors: {}",
                    importedCount, skippedCount, errorCount);
            return importedCount;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BatchResult processBatch(List<Map.Entry<Integer, List<String>>> batch) {
        int imported = 0;
        int skipped = 0;
        int errors = 0;

        for (Map.Entry<Integer, List<String>> entry : batch) {
            try {
                ProcessResult result = processRowSafely(entry.getValue(), entry.getKey());
                switch (result) {
                    case IMPORTED:
                        imported++;
                        break;
                    case SKIPPED:
                        skipped++;
                        break;
                    case ERROR:
                        errors++;
                        break;
                }
            } catch (Exception e) {
                errors++;
                log.error("Unexpected error processing row {}: {}", entry.getKey(), e.getMessage());
            }
        }

        return new BatchResult(imported, skipped, errors);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcessResult processRowSafely(List<String> rowData, int rowNumber) {
        try {
            // Check if we have enough columns and if the row has required data
            if (rowData.isEmpty() || rowData.size() < 33) {
                log.warn("Row {} has insufficient columns: {}", rowNumber, rowData.size());
                return ProcessResult.SKIPPED;
            }

            String productId = safeGet(rowData, 0);
            String productName = safeGet(rowData, 1);

            // Skip rows without required fields
            if (!isNotEmpty(productId) || !isNotEmpty(productName)) {
                log.debug("Skipping row {} - missing required fields (productId: '{}', productName: '{}')",
                        rowNumber, productId, productName);
                return ProcessResult.SKIPPED;
            }

            // Validate productId length and characters
            if (productId.length() > 500) { // Reasonable limit even for TEXT
                log.warn("Skipping row {} - productId too long: {} characters", rowNumber, productId.length());
                return ProcessResult.SKIPPED;
            }

            // Check if product already exists
            if (medicinalProductRepository.existsById(productId)) {
                log.debug("Product {} already exists, skipping", productId);
                return ProcessResult.SKIPPED;
            }

            // Create MedicinalProduct entity
            MedicinalProduct product = new MedicinalProduct();
            product.setProductId(productId);
            product.setProductName(productName);
            product.setCommonName(safeGet(rowData, 2));
            product.setPreparationType(safeGet(rowData, 3));
            product.setAnimalUsageBan(parseBooleanValue(safeGet(rowData, 4)));
            product.setPreviousName(safeGet(rowData, 5));
            product.setAdministrationRoute(safeGet(rowData, 6));
            product.setStrength(safeGet(rowData, 7));
            product.setPharmaceuticalForm(safeGet(rowData, 8));
            product.setProcedureType(safeGet(rowData, 9));
            product.setAuthorizationNumber(safeGet(rowData, 10));
            product.setAuthorizationValidity(safeGet(rowData, 11));
            product.setAtcCode(safeGet(rowData, 12));
            product.setPackaging(safeGet(rowData, 14));
            product.setActiveSubstance(safeGet(rowData, 15));
            product.setLegalBasis(safeGet(rowData, 24));
            product.setLeafletUrl(safeGet(rowData, 25));
            product.setCharacteristicsUrl(safeGet(rowData, 26));
            product.setLabelingLeafletUrl(safeGet(rowData, 27));
            product.setParallelImportLeafletUrl(safeGet(rowData, 28));
            product.setParallelImportLabelingUrl(safeGet(rowData, 29));
            product.setParallelImportPackagingUrl(safeGet(rowData, 30));
            product.setEducationalMaterialsProfessionalsUrl(safeGet(rowData, 31));
            product.setEducationalMaterialsPatientsUrl(safeGet(rowData, 32));

            // Save the product
            MedicinalProduct savedProduct = medicinalProductRepository.save(product);
            log.debug("Successfully saved product: {} - {}", savedProduct.getProductId(), savedProduct.getProductName());

            return ProcessResult.IMPORTED;

        } catch (Exception e) {
            log.error("Error creating product from row {}: {}", rowNumber, e.getMessage());
            log.debug("Row data for error: {}", rowData);
            return ProcessResult.ERROR;
        }
    }

    /**
     * Safely get value from list at index, returning empty string if index is out of bounds
     */
    private String safeGet(List<String> list, int index) {
        if (index >= 0 && index < list.size()) {
            String value = list.get(index);
            return value != null ? value.trim() : "";
        }
        return "";
    }

    /**
     * Parse boolean value from string - handles various formats
     */
    private Boolean parseBooleanValue(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }

        String lowerValue = value.toLowerCase().trim();
        return lowerValue.equals("true") ||
                lowerValue.equals("yes") ||
                lowerValue.equals("1") ||
                lowerValue.equals("tak") || // Polish "yes"
                lowerValue.equals("prawda"); // Polish "true"
    }

    /**
     * Get cell value as string with better error handling
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        try {
            switch (cell.getCellType()) {
                case STRING:
                    String stringValue = cell.getStringCellValue();
                    return stringValue != null ? stringValue.trim() : "";
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        // Handle both integer and decimal numbers
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {
                            return String.valueOf((long) numericValue);
                        } else {
                            return String.valueOf(numericValue);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        // Try to evaluate formula
                        return cell.getStringCellValue().trim();
                    } catch (Exception e) {
                        return cell.getCellFormula();
                    }
                case BLANK:
                    return "";
                default:
                    return "";
            }
        } catch (Exception e) {
            log.warn("Error reading cell value: {}", e.getMessage());
            return "";
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Helper classes
    public static class BatchResult {
        public final int imported;
        public final int skipped;
        public final int errors;

        public BatchResult(int imported, int skipped, int errors) {
            this.imported = imported;
            this.skipped = skipped;
            this.errors = errors;
        }
    }

    public enum ProcessResult {
        IMPORTED, SKIPPED, ERROR
    }
}