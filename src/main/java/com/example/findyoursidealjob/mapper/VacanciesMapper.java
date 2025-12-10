package com.example.findyoursidealjob.mapper;

import com.example.findyoursidealjob.dao.entity.Vacancies;
import com.example.findyoursidealjob.dto.VacanciesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface VacanciesMapper {

    @Mapping(source = "companies.id", target = "companiesId")
    @Mapping(source = "companies.name", target = "companyName")
    @Mapping(source = "categories.id", target = "categoriesId")
    @Mapping(source = "categories.name", target = "categoryName")
    @Mapping(source = "industries.id", target = "industriesId")
    @Mapping(source = "industries.name", target = "industryName")
    @Mapping(source = "vacanciesDetail.contactEmail", target = "contactEmail")
    VacanciesDto entityToDto(Vacancies vacancies);

    @Mapping(source = "companiesId", target = "companies.id")
    @Mapping(source = "categoriesId", target = "categories.id")
    @Mapping(source = "industriesId", target = "industries.id")
    Vacancies dtoToEntity(VacanciesDto vacanciesDto);
}
