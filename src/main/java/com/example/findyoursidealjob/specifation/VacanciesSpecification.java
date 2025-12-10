package com.example.findyoursidealjob.specifation;

import com.example.findyoursidealjob.dao.entity.Vacancies;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VacanciesSpecification {
    public Specification<Vacancies> search(String title) {

        return (root, query, cb) ->
                title == null || title.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");

    }
}

//        public Specification<Vacancies> search(String ) {
//
//            return (root, query, cb) ->
//                title == null || title.isBlank()
//                        ? cb.conjunction()
//                        : cb.like(cb.lower(root.get("name")), "%" + title.toLowerCase() + "%");
//
//
//    }
//

