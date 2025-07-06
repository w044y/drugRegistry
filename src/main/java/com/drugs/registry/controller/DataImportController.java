package com.drugs.registry.controller;

import com.drugs.registry.service.DataImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/import")
@Slf4j
@Tag(name = "Data Import", description = "Operations for importing pharmaceutical data")
@CrossOrigin(origins = "*")
public class DataImportController {
    @Autowired
    private final DataImportService dataImportService;

    public DataImportController(DataImportService dataImportService) {
        this.dataImportService = dataImportService;
    }

    @PostMapping("/excel")
    @Operation(summary = "Import pharmaceutical data from Excel file")
    public ResponseEntity<String> importFromExcel(@RequestParam("file") MultipartFile file) {
        try {


            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.badRequest().body("Only .xlsx files are supported");
            }

            // Call service to do the actual work
            int importedCount = dataImportService.importFromExcel(file);

            String message = String.format("Successfully imported %d pharmaceutical products", importedCount);

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Import failed: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    @Operation(summary = "Get import service status")
    public ResponseEntity<String> getImportStatus() {
        return ResponseEntity.ok("Import service is ready");
    }
}