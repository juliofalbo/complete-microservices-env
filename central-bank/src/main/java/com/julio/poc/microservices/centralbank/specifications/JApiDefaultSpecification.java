package com.julio.poc.microservices.centralbank.specifications;

import com.julio.poc.microservices.centralbank.specifications.domain.JApiFilter;
import com.julio.poc.microservices.centralbank.specifications.enums.JApiOperationEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class JApiDefaultSpecification<T> implements Specification<T> {

    private JApiFilter filter;

    public JApiDefaultSpecification(JApiFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (filter.getOperation().equals(JApiOperationEnum.BIGGER)) {
            return builder.greaterThanOrEqualTo(
                    root.<String>get(filter.getField()), filter.getValue().toString());
        } else if (filter.getOperation().equals(JApiOperationEnum.SMALLER)) {
            return builder.lessThanOrEqualTo(
                    root.<String>get(filter.getField()), filter.getValue().toString());
        } else if (filter.getOperation().equals(JApiOperationEnum.EQUAL)) {
            return builder.equal(root.get(filter.getField()), filter.getValue());
        } else if (filter.getOperation().equals(JApiOperationEnum.LIKE)) {
            return builder.like(
                    root.<String>get(filter.getField()), "%" + filter.getValue() + "%");
        }
        return null;
    }
}
