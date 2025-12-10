package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.VacanciesDetail;
import com.example.findyoursidealjob.dao.repository.VacanciesDetailRepository;
import com.example.findyoursidealjob.dto.VacanciesDetailDto;
import com.example.findyoursidealjob.mapper.VacanciesDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacanciesDetailService {

    private final VacanciesDetailRepository vacanciesDetailRepository;
    private final VacanciesDetailMapper vacanciesDetailMapper;

    // CREATE
    public VacanciesDetailDto createVacanciesDetail(VacanciesDetailDto dto) {
        VacanciesDetail entity = vacanciesDetailMapper.dtoToEntity(dto);
        VacanciesDetail saved = vacanciesDetailRepository.save(entity);
        return vacanciesDetailMapper.entityToDto(saved);
    }

    // GET BY ID
    public VacanciesDetailDto getVacanciesDetailById(Long id) {
        VacanciesDetail entity = vacanciesDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VacanciesDetail not found with id: " + id));
        return vacanciesDetailMapper.entityToDto(entity);
    }

    // UPDATE
    public VacanciesDetailDto updateVacanciesDetail(Long id, VacanciesDetailDto dto) {
        VacanciesDetail entity = vacanciesDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VacanciesDetail not found with id: " + id));

        entity.setResponsibilities(dto.getResponsibilities());
        entity.setRequirements(dto.getRequirements());
        entity.setPublishedDate(dto.getPublishedDate());
        entity.setDeadline(dto.getDeadline());
        entity.setNew(dto.isNew());
        entity.setMonthlyViews(dto.getMonthlyViews());

        VacanciesDetail updated = vacanciesDetailRepository.save(entity);
        return vacanciesDetailMapper.entityToDto(updated);
    }

    // DELETE
    public VacanciesDetailDto deleteVacanciesDetail(Long id) {
        VacanciesDetail entity = vacanciesDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VacanciesDetail not found with id: " + id));
        vacanciesDetailRepository.delete(entity);
        return vacanciesDetailMapper.entityToDto(entity);
    }

    // Şirkət üzrə vakansiya detalları
    public java.util.List<VacanciesDetailDto> getDetailsByCompanyId(Long companyId) {
        return vacanciesDetailRepository.findByCompanyId(companyId)
                .stream()
                .map(vacanciesDetailMapper::entityToDto)
                .toList();
    }

    // Kateqoriya üzrə vakansiya detalları
    public java.util.List<VacanciesDetailDto> getDetailsByCategoryId(Long categoryId) {
        return vacanciesDetailRepository.findByCategoryId(categoryId)
                .stream()
                .map(vacanciesDetailMapper::entityToDto)
                .toList();
    }

    // Sənaye üzrə vakansiya detalları
    public java.util.List<VacanciesDetailDto> getDetailsByIndustryId(Long industryId) {
        return vacanciesDetailRepository.findByIndustryId(industryId)
                .stream()
                .map(vacanciesDetailMapper::entityToDto)
                .toList();
    }

//
//    // GET ALL WITH PAGINATION
//    public Page<VacanciesDetailDto> getAllVacanciesDetail(Pageable pageable) {
//        return vacanciesDetailRepository.findAll(pageable)
//                .map(vacanciesDetailMapper::entityToDto);
//    }

}
