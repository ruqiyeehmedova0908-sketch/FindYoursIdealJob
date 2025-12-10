package com.example.findyoursidealjob.specifation;

import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dao.entity.Industries;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IndustriesSpecification {

    public Specification<Industries> search(String name) {

        return (root, query, cb) ->
                name == null || name.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");

    }
}
