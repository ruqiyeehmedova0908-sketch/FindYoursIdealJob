package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dao.entity.Industries;
import com.example.findyoursidealjob.dao.entity.Vacancies;
import com.example.findyoursidealjob.dao.repository.CategoriesRepository;
import com.example.findyoursidealjob.dao.repository.CompaniesRepository;
import com.example.findyoursidealjob.dao.repository.IndustriesRepository;
import com.example.findyoursidealjob.dao.repository.VacanciesRepository;
import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.dto.IndustriesDto;
import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.enums.DegreeOfDuty;
import com.example.findyoursidealjob.mapper.VacanciesMapper;
import com.example.findyoursidealjob.specifation.VacanciesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@lombok.extern.slf4j.Slf4j
public class VacanciesService {


        private final VacanciesRepository vacanciesRepository;
        private final VacanciesMapper vacanciesMapper;
        private final CompaniesRepository companiesRepository;
        private final CategoriesRepository categoriesRepository;
        private final IndustriesRepository industriesRepository;
        private final VacanciesSpecification vacanciesSpecification;
        private final com.example.findyoursidealjob.messaging.MessageProducer messageProducer;

        public VacanciesDto createVacancies(VacanciesDto vacanciesDto) {
            vacanciesRepository.findByTitle(vacanciesDto.getTitle())
                    .ifPresent(v -> { throw new RuntimeException("Vacancy already exists: " + v.getTitle()); });

            Vacancies vacancies = vacanciesMapper.dtoToEntity(vacanciesDto);

            if (vacanciesDto.getCompaniesId() != null)
                vacancies.setCompanies(new Companies(vacanciesDto.getCompaniesId()));
            if (vacanciesDto.getCategoriesId() != null)
                vacancies.setCategories(new Categories(vacanciesDto.getCategoriesId()));
            if (vacanciesDto.getIndustriesId() != null)
                vacancies.setIndustries(new Industries(vacanciesDto.getIndustriesId()));

            Vacancies saved = vacanciesRepository.save(vacancies);
            VacanciesDto result = vacanciesMapper.entityToDto(saved);
            
            try {
                messageProducer.sendVacancyCreatedMessage(result);
                messageProducer.sendNotification("Yeni vakansiya yaradıldı: " + result.getTitle());
            } catch (Exception e) {
                log.warn("RabbitMQ mesaj göndərilmədi: {}", e.getMessage());
            }
            
            return result;
        }

        public List<VacanciesDto> getAllVacancies() {
            return vacanciesRepository.findAllOrderByPublishedDateDesc()
                    .stream()
                    .map(vacanciesMapper::entityToDto)
                    .toList();
        }

        public org.springframework.data.domain.Page<VacanciesDto> getVacanciesPaged(
                int page, int size, String sortBy, String direction) {
            org.springframework.data.domain.Sort.Direction sortDirection = 
                direction.equalsIgnoreCase("ASC") ? 
                org.springframework.data.domain.Sort.Direction.ASC : 
                org.springframework.data.domain.Sort.Direction.DESC;
            
            org.springframework.data.domain.Pageable pageable = 
                org.springframework.data.domain.PageRequest.of(
                    page, 
                    size, 
                    org.springframework.data.domain.Sort.by(sortDirection, sortBy)
                );
            
            return vacanciesRepository.findAll(pageable)
                    .map(vacanciesMapper::entityToDto);
        }

        public VacanciesDto getVacancyById(Long id) {
            Vacancies vacancies = vacanciesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vacancy not found with id: " + id));
            return vacanciesMapper.entityToDto(vacancies);
        }

        public VacanciesDto updateVacancy(Long id, VacanciesDto dto) {
            Vacancies existing = vacanciesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vacancy not found with id: " + id));

            existing.setTitle(dto.getTitle());
            existing.setSalaryM(dto.getSalaryM());
            existing.setEmploymentType(dto.getEmploymentType());
            existing.setPublishedDate(dto.getPublishedDate());
            existing.setDeadline(dto.getDeadline());
            existing.setLocation(dto.getLocation());
            existing.setDegreeOfDuty(dto.getDegreeOfDuty());
            existing.setOrderBy(dto.getOrderBy());


            if (dto.getCompaniesId() != null)
                existing.setCompanies(new Companies(dto.getCompaniesId()));
            if (dto.getCategoriesId() != null)
                existing.setCategories(new Categories(dto.getCategoriesId()));
            if (dto.getIndustriesId() != null)
                existing.setIndustries(new Industries(dto.getIndustriesId()));

            Vacancies updated = vacanciesRepository.save(existing);
            return vacanciesMapper.entityToDto(updated);
        }
        public void deleteVacancy(Long id) {
            if (!vacanciesRepository.existsById(id)) {
                throw new RuntimeException("Vacancy not found with id: " + id);
            }
            vacanciesRepository.deleteById(id);
        }

        public List<VacanciesDto> searchVacancies(String title) {
            Specification<Vacancies> spec = vacanciesSpecification.search(title);
            return vacanciesRepository.findAll(spec)
                    .stream()
                    .map(vacanciesMapper::entityToDto)
                    .toList();
        }

        public List<VacanciesDto> getVacanciesByCompanyId(Long companyId) {
            return vacanciesRepository.findAll()
                    .stream()
                    .filter(v -> v.getCompanies() != null && v.getCompanies().getId().equals(companyId))
                    .map(vacanciesMapper::entityToDto)
                    .toList();
        }

        public List<VacanciesDto> getVacanciesByCategoryId(Long categoryId) {
            return vacanciesRepository.findAll()
                    .stream()
                    .filter(v -> v.getCategories() != null && v.getCategories().getId().equals(categoryId))
                    .map(vacanciesMapper::entityToDto)
                    .toList();
        }

        public List<VacanciesDto> getVacanciesByIndustryId(Long industryId) {
            return vacanciesRepository.findAll()
                    .stream()
                    .filter(v -> v.getIndustries() != null && v.getIndustries().getId().equals(industryId))
                    .map(vacanciesMapper::entityToDto)
                    .toList();
        }


    }



