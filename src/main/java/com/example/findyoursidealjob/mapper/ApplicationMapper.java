package com.example.findyoursidealjob.mapper;

import com.example.findyoursidealjob.dao.entity.Application;
import com.example.findyoursidealjob.dto.ApplicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "vacancies.id", target = "vacanciesId")
    ApplicationDto toDto(Application application);

    @Mapping(source = "vacanciesId", target = "vacancies.id")
    Application toEntity(ApplicationDto applicationDto);
}
