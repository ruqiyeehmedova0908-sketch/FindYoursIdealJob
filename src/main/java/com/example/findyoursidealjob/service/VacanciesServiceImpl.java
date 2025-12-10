package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Vacancies;
import com.example.findyoursidealjob.dao.repository.VacanciesRepository;
import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.exception.ResourceNotFoundException;
import com.example.findyoursidealjob.mapper.VacanciesMapper2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacanciesServiceImpl {
    private final VacanciesRepository vacanciesRepository;
    private final VacanciesMapper2 vacanciesMapper;

    public List<VacanciesDto> getAllVacancies() {
        return vacanciesRepository.findAll()
                .stream()
                .map(vacanciesMapper::toDto)
                .collect(Collectors.toList());
    }

    public VacanciesDto getVacanciesById(Long id) {
        Vacancies vacancies = vacanciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found"));
        return vacanciesMapper.toDto(vacancies);
    }

    public VacanciesDto createVacancies(VacanciesDto vacanciesDto) {
        Vacancies vacancies = vacanciesMapper.toEntity(vacanciesDto);
        Vacancies saved = vacanciesRepository.save(vacancies);
        return vacanciesMapper.toDto(saved);
    }

    public VacanciesDto updateVacancies(Long id, VacanciesDto vacanciesDto) {
        Vacancies vacancies = vacanciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found"));
        vacancies.setTitle(vacanciesDto.getTitle());
        vacancies.setDescription(vacanciesDto.getDescription());
        vacancies.setSalaryM(vacanciesDto.getSalaryM());
        vacancies.setRequirements(vacanciesDto.getRequirements());
        vacancies.setSalary(vacanciesDto.getSalary());
        vacancies.setCity(vacanciesDto.getCity());
        vacancies.setAddress(vacanciesDto.getAddress());
        vacancies.setDeadline(vacanciesDto.getDeadline());
        Vacancies updated = vacanciesRepository.save(vacancies);
        return vacanciesMapper.toDto(updated);
    }

    public void deleteVacancies(Long id) {
        if (!vacanciesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vacancy not found");
        }
        vacanciesRepository.deleteById(id);
    }
}
