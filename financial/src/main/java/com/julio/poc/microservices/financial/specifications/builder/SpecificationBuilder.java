package com.julio.poc.microservices.financial.specifications.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.data.jpa.domain.Specification;

import com.julio.poc.microservices.financial.specifications.CustomSpecification;
import com.julio.poc.microservices.financial.specifications.domain.FieldIn;
import com.julio.poc.microservices.financial.specifications.domain.SpecificationFilter;
import com.julio.poc.microservices.financial.specifications.enums.SpecificationOperationEnum;

public class SpecificationBuilder {

    private List<SpecificationFilter> filters = new ArrayList<>();

    public static SpecificationBuilder init() {
        return new SpecificationBuilder();
    }

    public SpecificationBuilder withEqualFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, SpecificationOperationEnum.EQUAL);
        return this;
    }

    public SpecificationBuilder withBiggerFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, SpecificationOperationEnum.BIGGER);
        return this;
    }

    public SpecificationBuilder withSmallerFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, SpecificationOperationEnum.SMALLER);
        return this;
    }

    public SpecificationBuilder withLikeFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, SpecificationOperationEnum.LIKE);
        return this;
    }

    public SpecificationBuilder withEqualInSubclassFilter(FieldIn inField, Object value) {
        addFilterIfValueIsNotNull(inField, value, SpecificationOperationEnum.EQUAL_SUBCLASS);
        return this;
    }

    public SpecificationBuilder withEqualInListFieldFilter(FieldIn inField, Object value) {
        addFilterIfValueIsNotNull(inField, value, SpecificationOperationEnum.IN);
        return this;
    }

    public SpecificationBuilder withCustomFilter(String name, Object value, SpecificationOperationEnum operation) {
        if (value != null) {
            SpecificationFilter field = new SpecificationFilter(name, value, operation);
            this.filters.add(field);
        }
        return this;
    }

    public SpecificationBuilder createFiltersByEntity(final Object object, SpecificationOperationEnum operationSpecificationEnum) {

        Arrays.asList(object.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                addFilterIfValueIsNotNull(field.getName(), field.get(object), operationSpecificationEnum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return this;
    }

    public List<SpecificationFilter> buildList() {
        return this.filters;
    }

    public <T> Specification<T> buildSpec() {
        if (filters.isEmpty()) {
            return Specification.where(null);
        }
        AtomicReference<Specification<T>> searchSpec = new AtomicReference<>(Specification.where(new CustomSpecification<T>(filters.get(0))));
        filters.remove(filters.get(0));

        filters.forEach(filter -> searchSpec.set(searchSpec.get().and(new CustomSpecification<>(filter))));
        return searchSpec.get();
    }

    private void addFilterIfValueIsNotNull(String field, Object value, SpecificationOperationEnum operation) {
        if (value != null) {
            SpecificationFilter fieldSpec = new SpecificationFilter(field, value, operation);
            this.filters.add(fieldSpec);
        }
    }

    private void addFilterIfValueIsNotNull(FieldIn fieldIn, Object value, SpecificationOperationEnum operationEnum) {
        if (value != null) {
            SpecificationFilter fieldSpec = new SpecificationFilter(fieldIn, value, operationEnum);
            this.filters.add(fieldSpec);
        }
    }

}