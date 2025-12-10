package com.example.findyoursidealjob.service;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.repository.CategoriesRepository;
import com.example.findyoursidealjob.dto.CategoriesDto;
import com.example.findyoursidealjob.dto.VacanciesDto;
import com.example.findyoursidealjob.exception.CategoryAlreadyExistsException;
import com.example.findyoursidealjob.mapper.CategoriesMapper;
import com.example.findyoursidealjob.mapper.VacanciesMapper;
import com.example.findyoursidealjob.specifation.CategoriesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;
    private final VacanciesMapper vacanciesMapper;
    private final CategoriesSpecification categoriesSpecification;

    public CategoriesDto createCategory(CategoriesDto categoriesDto) {

        categoriesRepository.findByName(categoriesDto.getName())
                .ifPresent(c -> {
                    throw new CategoryAlreadyExistsException("Category already exists: " + c.getName());
                });

        Categories entity = categoriesMapper.toEntity(categoriesDto);
        Categories saved = categoriesRepository.save(entity);

        return categoriesMapper.toDto(saved);
    }

    public List<CategoriesDto> getAllCategories() {
        return categoriesRepository.findAll()
                .stream()
                .map(categoriesMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoriesDto getCategoryById(Long id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoriesMapper.toDto(category);
    }

     public CategoriesDto updateCategory(Long id, CategoriesDto categoriesDto) {
        Categories existingCategory = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existingCategory.setName(categoriesDto.getName());

        Categories updated = categoriesRepository.save(existingCategory);

        return categoriesMapper.toDto(updated);
    }


    public CategoriesDto deleteCategory(Long id) {
        Categories existingCategory = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoriesRepository.delete(existingCategory);

        return categoriesMapper.toDto(existingCategory);
    }


    public List<VacanciesDto> getVacanciesByCategory(Long categoryId) {
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        return category.getVacancies()
                .stream()
                .map(vacanciesMapper::entityToDto)
                .collect(Collectors.toList());
    }


    public List<CategoriesDto> searchCategories(String name) {
        Specification<Categories> spec = categoriesSpecification.search(name);

        return categoriesRepository.findAll(spec)
                .stream()
                .map(categoriesMapper::toDto)
                .collect(Collectors.toList());
    }

}
