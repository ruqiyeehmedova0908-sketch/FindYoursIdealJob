package com.example.findyoursidealjob.mapper;

import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dto.CompaniesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompaniesMapper {

    Companies dtoToEntity(CompaniesDto companiesDto);

    CompaniesDto entityToDto(Companies companies);


}
