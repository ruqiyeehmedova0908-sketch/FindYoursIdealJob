package com.example.findyoursidealjob.controller;

import com.example.findyoursidealjob.dto.ApplicationDto;
import com.example.findyoursidealjob.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationDto applicationDto) {
        ApplicationDto created = applicationService.createApplication(applicationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplication(@PathVariable Long id) {
        ApplicationDto application = applicationService.getApplicationById(id);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/vacancy/{vacanciesId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByVacancy(@PathVariable Long vacanciesId) {
        List<ApplicationDto> applications = applicationService.getApplicationsByVacancy(vacanciesId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByEmail(@PathVariable String email) {
        List<ApplicationDto> applications = applicationService.getApplicationsByEmail(email);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationDto> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String reviewNotes) {
        ApplicationDto updated = applicationService.updateApplicationStatus(id, status, reviewNotes);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
