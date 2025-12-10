package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.repository.CategoriesRepository;
import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.exception.ResourceNotFoundException;
import com.example.findyoursidealjob.mapper.CategoriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;

    public List<CategoriesDto> getAllCategories() {
        return categoriesRepository.findAll()
                .stream()
                .map(categoriesMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoriesDto getCategoriesById(Long id) {
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoriesMapper.toDto(categories);
    }

    public CategoriesDto createCategories(CategoriesDto categoriesDto) {
        Categories categories = categoriesMapper.toEntity(categoriesDto);
        Categories saved = categoriesRepository.save(categories);
        return categoriesMapper.toDto(saved);
    }

    public CategoriesDto updateCategories(Long id, CategoriesDto categoriesDto) {
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categories.setName(categoriesDto.getName());
//        categories.setDescription(categoriesDto.getDescription());
        categories.setIcon(categoriesDto.getIcon());
        Categories updated = categoriesRepository.save(categories);
        return categoriesMapper.toDto(updated);
    }

    public void deleteCategories(Long id) {
        if (!categoriesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoriesRepository.deleteById(id);
    }

}
