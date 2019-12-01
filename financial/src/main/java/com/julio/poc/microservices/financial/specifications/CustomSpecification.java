package com.julio.poc.microservices.financial.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.julio.poc.microservices.financial.specifications.domain.SpecificationFilter;
import com.julio.poc.microservices.financial.specifications.enums.SpecificationOperationEnum;

// Actually this is a library that I've created a few months ago, but I prefer to use the code directly here to facilitate the understand
// https://github.com/juliofalbo/j-api-filter
public class CustomSpecification<T> implements Specification<T> {

    private SpecificationFilter filter;

    public CustomSpecification(SpecificationFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (filter.getOperation().equals(SpecificationOperationEnum.BIGGER)) {
            return builder.greaterThanOrEqualTo(
                    root.get(filter.getField()), filter.getValue().toString());
        } else if (filter.getOperation().equals(SpecificationOperationEnum.SMALLER)) {
            return builder.lessThanOrEqualTo(
                    root.get(filter.getField()), filter.getValue().toString());
        } else if (filter.getOperation().equals(SpecificationOperationEnum.EQUAL)) {
            return builder.equal(root.get(filter.getField()), filter.getValue());
        } else if (filter.getOperation().equals(SpecificationOperationEnum.LIKE)) {
            return builder.like(
                    root.get(filter.getField()), "%" + filter.getValue() + "%");
        } else if (filter.getOperation().equals(SpecificationOperationEnum.IN)) {
            return builder.equal(root
                    .join(filter.getFieldIn().getFieldList()).get(filter.getFieldIn().getAttributeName()), filter.getValue());
        } else if (filter.getOperation().equals(SpecificationOperationEnum.EQUAL_SUBCLASS)) {
            return builder.equal(root
                    .get(filter.getFieldIn().getFieldList()).get(filter.getFieldIn().getAttributeName()), filter.getValue());
        }
        return null;
    }

}