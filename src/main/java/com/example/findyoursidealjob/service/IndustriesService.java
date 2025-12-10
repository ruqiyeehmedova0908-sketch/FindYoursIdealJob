package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dao.entity.Industries;
import com.example.findyoursidealjob.dao.repository.IndustriesRepository;
import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.dto.CompaniesDto;
import com.example.findyoursidealjob.dto.IndustriesDto;
import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.mapper.IndustriesMapper;
import com.example.findyoursidealjob.mapper.VacanciesMapper;
import com.example.findyoursidealjob.specifation.IndustriesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndustriesService {

    private final IndustriesRepository industriesRepository;
    private final IndustriesMapper industriesMapper;
    private final IndustriesSpecification industriesSpecification;
    private final VacanciesMapper vacanciesMapper;

    public IndustriesDto createIndustry(IndustriesDto industriesDto) {
        industriesRepository.findByName(industriesDto.getName())
                .ifPresent(c -> {
                    throw new RuntimeException("Industry already exists: " + c.getName());
                });

        Industries entity = industriesMapper.toEntity(industriesDto);
        Industries saved = industriesRepository.save(entity);

        return industriesMapper.toDto(saved);
    }

    public List<IndustriesDto> getAllIndustries( ){
        return  industriesRepository.findAll()
                .stream()
                .map(industriesMapper::toDto)
                .collect(Collectors.toList());
    }

    public IndustriesDto getFindById(Long id){
        Industries industries= industriesRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Industries not found with id: " + id));
        return industriesMapper.toDto(industries);

    }

    public IndustriesDto deleteIndustriesById(Long id){
        Industries deleted = industriesRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Industries not found with id: " + id));

        industriesRepository.delete(deleted);

        return industriesMapper.toDto(deleted);
    }

    public List<VacanciesDto> getVacanciesByIndustries(Long industriesId) {
        Industries industries = industriesRepository.findById(industriesId)
                .orElseThrow(() -> new RuntimeException("Industries not found with id: " + industriesId));

        return industries.getVacancies()
                .stream()
                .map(vacanciesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<IndustriesDto> searchIndustries(String name) {
        Specification<Industries> spec = industriesSpecification.search(name);

        return industriesRepository.findAll(spec)
                .stream()
                .map(industriesMapper::toDto)
                .collect(Collectors.toList());
    }

    public IndustriesDto updateIndustries(Long id, IndustriesDto industriesDto) {
        Industries existingIndustries = industriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Industries not found with id: " + id));

        existingIndustries.setName(industriesDto.getName());

        Industries updated = industriesRepository.save(existingIndustries);

        return industriesMapper.toDto(updated);
    }

}
