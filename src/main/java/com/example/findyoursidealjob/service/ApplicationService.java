package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Application;
import com.example.findyoursidealjob.dao.entity.Vacancies;
import com.example.findyoursidealjob.dao.repository.ApplicationRepository;
import com.example.findyoursidealjob.dao.repository.VacanciesRepository;
import com.example.findyoursidealjob.dto.ApplicationDto;
import com.example.findyoursidealjob.exception.ResourceNotFoundException;
import com.example.findyoursidealjob.mapper.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final VacanciesRepository vacanciesRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationDto createApplication(ApplicationDto applicationDto) {
        Vacancies vacancies = vacanciesRepository.findById(applicationDto.getVacanciesId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found"));

        Application application = new Application();
        application.setVacancies(vacancies);
        application.setApplicantName(applicationDto.getApplicantName());
        application.setApplicantEmail(applicationDto.getApplicantEmail());
        application.setCoverLetter(applicationDto.getCoverLetter());
        application.setResumeUrl(applicationDto.getResumeUrl());
        application.setAppliedDate(LocalDateTime.now());
        application.setStatus("PENDING");

        Application saved = applicationRepository.save(application);
        return applicationMapper.toDto(saved);
    }

    public ApplicationDto getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        return applicationMapper.toDto(application);
    }

    public List<ApplicationDto> getApplicationsByVacancy(Long vacanciesId) {
        return applicationRepository.findByVacanciesId(vacanciesId)
                .stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getApplicationsByEmail(String email) {
        return applicationRepository.findByApplicantEmail(email)
                .stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApplicationDto updateApplicationStatus(Long id, String status, String reviewNotes) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        
        application.setStatus(status);
        application.setReviewNotes(reviewNotes);
        application.setReviewedDate(LocalDateTime.now());

        Application updated = applicationRepository.save(application);
        return applicationMapper.toDto(updated);
    }

    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found");
        }
        applicationRepository.deleteById(id);
    }
}
