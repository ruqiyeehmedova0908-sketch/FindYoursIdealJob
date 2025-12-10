package com.example.findyoursidealjob.mapper;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dto.CategoriesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {
    CategoriesDto toDto(Categories categories);
    
    @Mapping(target = "vacancies", ignore = true)
    Categories toEntity(CategoriesDto categoriesDto);
}
