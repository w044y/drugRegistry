
package com.drugs.registry.controller;

import com.drugs.registry.entity.MedicinalProduct;
import com.drugs.registry.repository.MedicinalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class MedicinalProductController {

    @Autowired
    private MedicinalProductRepository repository;

    @GetMapping
    public ResponseEntity<Page<MedicinalProduct>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MedicinalProduct> products = repository.findAll(pageRequest);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<MedicinalProduct> getProductById(@PathVariable Long productId) {
        Optional<MedicinalProduct> product = repository.findById(productId);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        long count = repository.count();
        return ResponseEntity.ok(count);
    }
}