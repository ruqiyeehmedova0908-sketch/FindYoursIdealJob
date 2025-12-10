package com.example.findyoursidealjob.mapper;
import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dao.entity.Vacancies;
import com.example.findyoursidealjob.dao.entity.VacanciesDetail;
import com.example.findyoursidealjob.dto.CompaniesDto;
import com.example.findyoursidealjob.dto.VacanciesDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VacanciesDetailMapper {
    @Mapping(target = "vacancies", ignore = true)
    VacanciesDetail dtoToEntity(VacanciesDetailDto vacanciesDetailDto);

    @Mapping(source = "vacancies.id", target = "vacanciesId")
    VacanciesDetailDto entityToDto(VacanciesDetail vacanciesDetail);

}
