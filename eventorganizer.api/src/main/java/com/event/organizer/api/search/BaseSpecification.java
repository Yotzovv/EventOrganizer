package com.event.organizer.api.search;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class BaseSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public BaseSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate
      (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase("=")) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase("!=")) {
            return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase("!:")) { // If not in collection
            if (criteria.getValue().toString() == "[]") {
                return null;
            }
            Predicate predicate = root.get(criteria.getKey()).in(criteria.getValue()).not();
            return predicate;
        }
        return null;
    }
}