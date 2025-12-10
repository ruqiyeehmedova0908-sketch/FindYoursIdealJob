package com.example.findyoursidealjob.controller;

import com.example.findyoursidealjob.dto.IndustriesDto;
import com.example.findyoursidealjob.service.IndustriesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/industries")
@RequiredArgsConstructor
public class IndustriesController {
    private final IndustriesServiceImpl industriesService;

    @GetMapping
    public ResponseEntity<List<IndustriesDto>> getAllIndustries() {
        List<IndustriesDto> industries = industriesService.getAllIndustries();
        return ResponseEntity.ok(industries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustriesDto> getIndustries(@PathVariable Long id) {
        IndustriesDto industries = industriesService.getIndustriesById(id);
        return ResponseEntity.ok(industries);
    }

    @PostMapping
    public ResponseEntity<IndustriesDto> createIndustries(@RequestBody IndustriesDto industriesDto) {
        IndustriesDto created = industriesService.createIndustries(industriesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndustriesDto> updateIndustries(@PathVariable Long id, @RequestBody IndustriesDto industriesDto) {
        IndustriesDto updated = industriesService.updateIndustries(id, industriesDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndustries(@PathVariable Long id) {
        industriesService.deleteIndustries(id);
        return ResponseEntity.noContent().build();
    }

}
