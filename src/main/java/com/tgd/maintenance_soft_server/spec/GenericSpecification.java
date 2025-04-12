package com.tgd.maintenance_soft_server.spec;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GenericSpecification<T> {
    public Specification<T> buildSpecification(Map<String, Object> filters, String search, List<String> searchableFields) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Apply specific filters
            filters.forEach((key, value) -> {
                if (value != null) {
                    predicates.add(createPredicate(root, cb, key, value));
                }
            });

            // Apply global search if `search` and `searchableFields` are present
            if (search != null && !search.trim().isEmpty() && searchableFields != null) {
                predicates.add(createSearchPredicate(root, cb, search, searchableFields));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate createPredicate(Root<T> root, CriteriaBuilder cb, String field, Object value) {
        Path<?> path = getPath(root, field);
        Class<?> fieldType = path.getJavaType();

        // If it's LocalDate and the value is a String, try to parse it
        if (fieldType.equals(LocalDate.class) && value instanceof String) {
            try {
                LocalDate dateValue = LocalDate.parse(value.toString());
                return cb.equal(path, dateValue);
            } catch (DateTimeParseException ignored) {
            }
        }

        return cb.equal(path, value);
    }


    private Predicate createSearchPredicate(Root<T> root, CriteriaBuilder cb, String search, List<String> searchableFields) {
        List<Predicate> searchPredicates = new ArrayList<>();

        for (String field : searchableFields) {
            try {
                Path<?> path = getPath(root, field);

                Expression<String> fieldAsString = path.as(String.class);
                Predicate predicate = cb.like(cb.lower(fieldAsString), "%" + search.toLowerCase() + "%");

                searchPredicates.add(predicate);
            } catch (IllegalArgumentException ignored) {

            }
        }

        return searchPredicates.isEmpty() ? cb.conjunction() : cb.or(searchPredicates.toArray(new Predicate[0]));
    }

    private Path<String> getPath(Root<T> root, String field) {
        String[] parts = field.split("\\.");
        Path<String> path = root.get(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }

        return path;
    }
}
