package com.example.findyoursidealjob.mapper;

import com.example.findyoursidealjob.dao.entity.Vacancies;
import com.example.findyoursidealjob.dto.VacanciesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacanciesMapper2 {
    @Mapping(source = "companies.id", target = "companiesId")
    @Mapping(source = "industries.id", target = "industriesId")
    @Mapping(source = "categories.id", target = "categoriesId")
    VacanciesDto toDto(Vacancies vacancies);

    @Mapping(source = "companiesId", target = "companies.id")
    @Mapping(source = "industriesId", target = "industries.id")
    @Mapping(source = "categoriesId", target = "categories.id")
    @Mapping(target = "vacanciesDetail", ignore = true)
    Vacancies toEntity(VacanciesDto vacanciesDto);
}
