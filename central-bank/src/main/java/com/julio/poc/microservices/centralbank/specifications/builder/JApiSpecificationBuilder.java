package com.julio.poc.microservices.centralbank.specifications.builder;

import com.julio.poc.microservices.centralbank.specifications.JApiDefaultSpecification;
import com.julio.poc.microservices.centralbank.specifications.domain.JApiFilter;
import com.julio.poc.microservices.centralbank.specifications.enums.JApiOperationEnum;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JApiSpecificationBuilder {

    private List<JApiFilter> filters = new ArrayList<>();

    public static JApiSpecificationBuilder init() {
        return new JApiSpecificationBuilder();
    }

    public JApiSpecificationBuilder withEqualFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, JApiOperationEnum.EQUAL);
        return this;
    }

    public JApiSpecificationBuilder withBiggerFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, JApiOperationEnum.BIGGER);
        return this;
    }

    public JApiSpecificationBuilder withSmallerFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, JApiOperationEnum.SMALLER);
        return this;
    }

    public JApiSpecificationBuilder withLikeFilter(String name, Object value) {
        addFilterIfValueIsNotNull(name, value, JApiOperationEnum.LIKE);
        return this;
    }

    public JApiSpecificationBuilder withCustomFilter(String name, Object value, JApiOperationEnum operation) {
        if (value != null) {
            JApiFilter field = new JApiFilter(name, operation, value);
            this.filters.add(field);
        }
        return this;
    }

    public JApiSpecificationBuilder createFiltersByEntity(final Object object, JApiOperationEnum operationSpecificationEnum) {

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

    public List<JApiFilter> buildList() {
        return this.filters;
    }

    public <T> Specification<T> buildSpec() {
        if (filters.isEmpty()) {
            return Specification.where(null);
        }
        AtomicReference<Specification<T>> searchSpec = new AtomicReference<>(Specification.where(new JApiDefaultSpecification<T>(filters.get(0))));
        filters.remove(filters.get(0));

        filters.forEach(filter -> searchSpec.set(searchSpec.get().and(new JApiDefaultSpecification<>(filter))));
        return searchSpec.get();
    }

    private void addFilterIfValueIsNotNull(String field, Object value, JApiOperationEnum operation) {
        if (value != null) {
            JApiFilter fieldSpec = new JApiFilter(field, operation, value);
            this.filters.add(fieldSpec);
        }
    }

}
