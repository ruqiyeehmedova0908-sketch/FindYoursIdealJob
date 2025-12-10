package com.example.findyoursidealjob.controller;

import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.service.VacanciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacanciesController {

    private final VacanciesService vacanciesService;

    // Vakansiya yaratmaq
    @PostMapping
    public ResponseEntity<VacanciesDto> createVacancy(@RequestBody VacanciesDto dto) {
        VacanciesDto created = vacanciesService.createVacancies(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Bütün vakansiyaları almaq
    @GetMapping
    public ResponseEntity<List<VacanciesDto>> getAllVacancies() {
        return ResponseEntity.ok(vacanciesService.getAllVacancies());
    }

    // Pageable ilə vakansiyalar
    @GetMapping("/paged")
    public ResponseEntity<org.springframework.data.domain.Page<VacanciesDto>> getVacanciesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        return ResponseEntity.ok(vacanciesService.getVacanciesPaged(page, size, sortBy, direction));
    }

    // ID üzrə vakansiya almaq
    @GetMapping("/{id}")
    public ResponseEntity<VacanciesDto> getVacancyById(@PathVariable Long id) {
        return ResponseEntity.ok(vacanciesService.getVacancyById(id));
    }

    // Vakansiya yeniləmək
    @PutMapping("/{id}")
    public ResponseEntity<VacanciesDto> updateVacancy(@PathVariable Long id, @RequestBody VacanciesDto dto) {
        return ResponseEntity.ok(vacanciesService.updateVacancy(id, dto));
    }

    // Vakansiya silmək
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        vacanciesService.deleteVacancy(id);
        return ResponseEntity.noContent().build();
    }

    // Title üzrə axtarış
    @GetMapping("/search")
    public ResponseEntity<List<VacanciesDto>> searchVacancies(@RequestParam String title) {
        return ResponseEntity.ok(vacanciesService.searchVacancies(title));
    }

    // Şirkət üzrə vakansiyalar
    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<VacanciesDto>> getVacanciesByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(vacanciesService.getVacanciesByCompanyId(companyId));
    }

    // Kateqoriya üzrə vakansiyalar
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<VacanciesDto>> getVacanciesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(vacanciesService.getVacanciesByCategoryId(categoryId));
    }

    // Sənaye üzrə vakansiyalar
    @GetMapping("/by-industry/{industryId}")
    public ResponseEntity<List<VacanciesDto>> getVacanciesByIndustry(@PathVariable Long industryId) {
        return ResponseEntity.ok(vacanciesService.getVacanciesByIndustryId(industryId));
    }


}
