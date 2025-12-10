package com.example.findyoursidealjob.specifation;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.entity.Companies;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CompaniesSpecification {
    public Specification<Companies> search(String name) {

        return (root, query, cb) ->
            name == null || name.isBlank()
            ? cb.conjunction()
            : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");


         }
}

