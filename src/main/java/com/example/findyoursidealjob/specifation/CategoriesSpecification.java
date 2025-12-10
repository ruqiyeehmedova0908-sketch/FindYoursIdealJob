package com.example.findyoursidealjob.specifation;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class CategoriesSpecification {

        public Specification<Categories> search(String name) {
            return (root, query, cb) ->
                    name == null || name.isBlank()
                            ? cb.conjunction()
                            : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");

        }
}

