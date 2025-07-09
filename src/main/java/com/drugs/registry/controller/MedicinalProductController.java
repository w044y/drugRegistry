package com.drugs.registry.controller;

import com.drugs.registry.entity.MedicinalProduct;
import com.drugs.registry.repository.MedicinalProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class MedicinalProductController {

    private final MedicinalProductRepository medicinalProductRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() :
                    Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<MedicinalProduct> productPage = medicinalProductRepository.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("products", productPage.getContent());
            response.put("currentPage", productPage.getNumber());
            response.put("totalPages", productPage.getTotalPages());
            response.put("totalElements", productPage.getTotalElements());
            response.put("size", productPage.getSize());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error retrieving products", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to retrieve products: " + e.getMessage()));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        try {
            Optional<MedicinalProduct> product = medicinalProductRepository.findById(productId);

            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            log.error("Error retrieving product with ID: {}", productId, e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to retrieve product: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String q) {
        try {
            if (q == null || q.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Search query cannot be empty"));
            }

            List<MedicinalProduct> products = medicinalProductRepository.searchProducts(q.trim());

            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("count", products.size());
            response.put("searchTerm", q);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error searching products with query: {}", q, e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Search failed: " + e.getMessage()));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            long totalProducts = medicinalProductRepository.count();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalProducts", totalProducts);

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            log.error("Error retrieving stats", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to retrieve stats: " + e.getMessage()));
        }
    }
}