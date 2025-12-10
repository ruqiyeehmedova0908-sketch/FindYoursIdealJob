package com.example.findyoursidealjob.controller;

import com.example.findyoursidealjob.dto.VacanciesDetailDto;
import com.example.findyoursidealjob.service.VacanciesDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacancies-detail")
@RequiredArgsConstructor
public class VacanciesDetailController {

    private final VacanciesDetailService vacanciesDetailService;

    // CREATE
    @PostMapping
    public ResponseEntity<VacanciesDetailDto> createVacanciesDetail(@RequestBody VacanciesDetailDto dto) {
        VacanciesDetailDto created = vacanciesDetailService.createVacanciesDetail(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<VacanciesDetailDto> getVacanciesDetailById(@PathVariable Long id) {
        VacanciesDetailDto dto = vacanciesDetailService.getVacanciesDetailById(id);
        return ResponseEntity.ok(dto);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<VacanciesDetailDto> updateVacanciesDetail(@PathVariable Long id,
                                                     @RequestBody VacanciesDetailDto dto) {
        VacanciesDetailDto updated = vacanciesDetailService.updateVacanciesDetail(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<VacanciesDetailDto> deleteVacanciesDetail(@PathVariable Long id) {
        VacanciesDetailDto deleted = vacanciesDetailService.deleteVacanciesDetail(id);
        return ResponseEntity.ok(deleted);
    }

    // GET ALL WITH PAGINATION
//    @GetMapping
//    public ResponseEntity<Page<VacanciesDetailDto>> getAllVacanciesDetail(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        Page<VacanciesDetailDto> all = vacanciesDetailService.getAllVacanciesDetail(PageRequest.of(page, size));
//        return ResponseEntity.ok(all);
//    }

    // Şirkət üzrə vakansiya detalları
    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<java.util.List<VacanciesDetailDto>> getDetailsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(vacanciesDetailService.getDetailsByCompanyId(companyId));
    }

    // Kateqoriya üzrə vakansiya detalları
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<java.util.List<VacanciesDetailDto>> getDetailsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(vacanciesDetailService.getDetailsByCategoryId(categoryId));
    }

    // Sənaye üzrə vakansiya detalları
    @GetMapping("/by-industry/{industryId}")
    public ResponseEntity<java.util.List<VacanciesDetailDto>> getDetailsByIndustry(@PathVariable Long industryId) {
        return ResponseEntity.ok(vacanciesDetailService.getDetailsByIndustryId(industryId));
    }
}
