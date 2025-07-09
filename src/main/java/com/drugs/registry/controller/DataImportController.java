// DataImportController.java
package com.drugs.registry.controller;

import com.drugs.registry.service.DataImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
@Slf4j
public class DataImportController {

    private final DataImportService dataImportService;

    @PostMapping("/excel")
    public ResponseEntity<?> importFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "File is empty"));
            }

            if (!isValidExcelFile(file)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid file format. Please upload an Excel file (.xlsx)"));
            }

            log.info("Starting import for file: {} (size: {} bytes)",
                    file.getOriginalFilename(), file.getSize());

            // Perform import
            int importedCount = dataImportService.importFromExcel(file);

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Import completed successfully");
            response.put("importedCount", importedCount);
            response.put("fileName", file.getOriginalFilename());

            log.info("Import completed successfully. Records imported: {}", importedCount);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error during Excel import", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Import failed: " + e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    private boolean isValidExcelFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && (
                filename.toLowerCase().endsWith(".xlsx") ||
                        filename.toLowerCase().endsWith(".xls")
        );
    }
}
