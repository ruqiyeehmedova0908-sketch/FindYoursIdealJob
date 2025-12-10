package com.example.findyoursidealjob.controller;

import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.dto.CompaniesDto;
import com.example.findyoursidealjob.dto.IndustriesDto;
import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.service.CategoriesService;
import com.example.findyoursidealjob.service.CompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompaniesController {


        private final CompaniesService companiesService;

        @PostMapping
        public ResponseEntity<CompaniesDto> createCompany(@RequestBody CompaniesDto companiesDto) {
            CompaniesDto created = companiesService.createCompany(companiesDto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<List<CompaniesDto>> getAllCompanies() {
            List<CompaniesDto> list = companiesService.getAllCompanies();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }

        @GetMapping("/paged")
        public ResponseEntity<org.springframework.data.domain.Page<CompaniesDto>> getCompaniesPaged(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "name") String sortBy) {
            return ResponseEntity.ok(companiesService.getCompaniesPaged(page, size, sortBy));
        }

        @GetMapping("/{id}")
        public ResponseEntity<CompaniesDto> getFindById(@PathVariable Long id) {
            CompaniesDto companies = companiesService.getFindById(id);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<CompaniesDto> updateCompanies(@PathVariable Long id, @RequestBody CompaniesDto companiesDto) {
            CompaniesDto updated = companiesService.updateCompanies(id, companiesDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }

        // DELETE CATEGORY
        @DeleteMapping("/{id}")
        public ResponseEntity<CompaniesDto> deleteCompaniesById(@PathVariable Long id) {
            CompaniesDto deleted = companiesService.deleteCompaniesById(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }

        // GET VACANCIES BY CATEGORY
        @GetMapping("/{id}/vacancies")
        public ResponseEntity<List<VacanciesDto>> getVacanciesByCategory(@PathVariable Long id) {
            List<VacanciesDto> vacancies = companiesService.getVacanciesByCategory(id);
            return new ResponseEntity<>(vacancies, HttpStatus.OK);
        }

        //SPECIFICATION
        @GetMapping("/search")
        public ResponseEntity<List<CompaniesDto>> searchCompanies(@RequestParam(required = false) String name) {
            List<CompaniesDto> result = companiesService.searchCompanies(name);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

