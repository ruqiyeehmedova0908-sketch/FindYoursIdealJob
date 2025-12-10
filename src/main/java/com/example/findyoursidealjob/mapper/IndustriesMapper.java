package com.example.findyoursidealjob.mapper;

import com.example.findyoursidealjob.dao.entity.Industries;
import com.example.findyoursidealjob.dto.IndustriesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IndustriesMapper {
    IndustriesDto toDto(Industries industries);
    
    @Mapping(target = "vacancies", ignore = true)
    Industries toEntity(IndustriesDto industriesDto);
}
