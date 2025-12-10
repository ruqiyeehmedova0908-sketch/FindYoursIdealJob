package com.example.findyoursidealjob.controller;

import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.service.CategoriesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesServiceImpl categoriesService;

    @GetMapping
    public ResponseEntity<List<CategoriesDto>> getAllCategories() {
        List<CategoriesDto> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDto> getCategories(@PathVariable Long id) {
        CategoriesDto categories = categoriesService.getCategoriesById(id);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoriesDto> createCategories(@RequestBody CategoriesDto categoriesDto) {
        CategoriesDto created = categoriesService.createCategories(categoriesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDto> updateCategories(@PathVariable Long id, @RequestBody CategoriesDto categoriesDto) {
        CategoriesDto updated = categoriesService.updateCategories(id, categoriesDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable Long id) {
        categoriesService.deleteCategories(id);
        return ResponseEntity.noContent().build();
    }

}
