package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Industries;
import com.example.findyoursidealjob.dao.repository.IndustriesRepository;
import com.example.findyoursidealjob.dto.IndustriesDto;
import com.example.findyoursidealjob.exception.ResourceNotFoundException;
import com.example.findyoursidealjob.mapper.IndustriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndustriesServiceImpl {
    private final IndustriesRepository industriesRepository;
    private final IndustriesMapper industriesMapper;

    public List<IndustriesDto> getAllIndustries() {
        return industriesRepository.findAll()
                .stream()
                .map(industriesMapper::toDto)
                .collect(Collectors.toList());
    }

    public IndustriesDto getIndustriesById(Long id) {
        Industries industries = industriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Industry not found"));
        return industriesMapper.toDto(industries);
    }

    public IndustriesDto createIndustries(IndustriesDto industriesDto) {
        Industries industries = industriesMapper.toEntity(industriesDto);
        Industries saved = industriesRepository.save(industries);
        return industriesMapper.toDto(saved);
    }

    public IndustriesDto updateIndustries(Long id, IndustriesDto industriesDto) {
        Industries industries = industriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Industry not found"));
        industries.setName(industriesDto.getName());
        industries.setIcon(industriesDto.getIcon());
        Industries updated = industriesRepository.save(industries);
        return industriesMapper.toDto(updated);
    }

    public void deleteIndustries(Long id) {
        if (!industriesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Industry not found");
        }
        industriesRepository.deleteById(id);
    }

}
