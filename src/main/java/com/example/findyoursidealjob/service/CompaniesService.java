package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dao.repository.CompaniesRepository;
import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.dto.CompaniesDto;
import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.exception.CompaniesAlreadyExistsException;
import com.example.findyoursidealjob.mapper.CompaniesMapper;
import com.example.findyoursidealjob.mapper.VacanciesMapper;
import com.example.findyoursidealjob.specifation.CompaniesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompaniesService {

    private final CompaniesRepository companiesRepository;
    private final CompaniesMapper companiesMapper;
    private final CompaniesSpecification companiesSpecification;
    private final VacanciesMapper vacanciesMapper;

    public CompaniesDto createCompany(CompaniesDto companiesDto) {

        companiesRepository.findByName(companiesDto.getName())
                .ifPresent(c -> {
                    throw new CompaniesAlreadyExistsException("Company already exists: " + c.getName());
                });

        Companies entity = companiesMapper.dtoToEntity(companiesDto);
        Companies saved = companiesRepository.save(entity);

        return companiesMapper.entityToDto(saved);

    }
    public List<CompaniesDto> getAllCompanies(){
                return  companiesRepository.findAll()
                .stream()
                .map(companiesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public CompaniesDto getFindById(Long id){
       Companies companies= companiesRepository
               .findById(id).orElseThrow(() -> new RuntimeException("Companies not found with id: " + id));
        return companiesMapper.entityToDto(companies);

    }

    public CompaniesDto deleteCompaniesById(Long id){
        Companies deleted = companiesRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Companies not found with id: " + id));

        companiesRepository.delete(deleted);

        return companiesMapper.entityToDto(deleted);
    }

    public List<VacanciesDto> getVacanciesByCategory(Long companiesId) {
        Companies companies = companiesRepository.findById(companiesId)
                .orElseThrow(() -> new RuntimeException("Companies not found with id: " + companiesId));

        return companies.getVacancies()
                .stream()
                .map(vacanciesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<CompaniesDto> searchCompanies(String name) {
        Specification<Companies> spec = companiesSpecification.search(name);

        return companiesRepository.findAll(spec)
                .stream()
                .map(companiesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public CompaniesDto updateCompanies(Long id, CompaniesDto companiesDto) {
        Companies existingCompanies = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Companies not found with id: " + id));

        existingCompanies.setName(companiesDto.getName());

        existingCompanies.setIcon(companiesDto.getIcon());

        Companies updated = companiesRepository.save(existingCompanies);

        return companiesMapper.entityToDto(updated);
    }

    public org.springframework.data.domain.Page<CompaniesDto> getCompaniesPaged(
            int page, int size, String sortBy) {
        org.springframework.data.domain.Pageable pageable = 
            org.springframework.data.domain.PageRequest.of(
                page, 
                size, 
                org.springframework.data.domain.Sort.by(sortBy)
            );
        
        return companiesRepository.findAll(pageable)
                .map(companiesMapper::entityToDto);
    }

}