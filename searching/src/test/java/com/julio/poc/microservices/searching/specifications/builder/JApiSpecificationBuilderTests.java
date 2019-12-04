package com.julio.poc.microservices.searching.specifications.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class JApiSpecificationBuilderTests {

    @Test
    public void testListWithOneFilter() {
        Integer count = SpecificationBuilder.init().withEqualFilter("teste", true).buildList().size();
        assertThat(count, equalTo(1));
    }

    @Test
    public void should_return_a_list_with_two_filter() {
        Integer count = SpecificationBuilder.init()
                .withEqualFilter("teste", true)
                .withEqualFilter("teste2", "teste")
                .buildList().size();
        assertThat(count, equalTo(2));
    }

    @Test
    public void should_return_a_list_with_two_filter_but_one_value_is_null() {
        Integer count = SpecificationBuilder.init()
                .withEqualFilter("teste", true)
                .withEqualFilter("teste2", null)
                .buildList().size();
        assertThat(count, equalTo(1));
    }

    @Test
    public void should_return_a_list_with_three_filter_but_one_value_is_null() {
        Integer count = SpecificationBuilder.init()
                .withEqualFilter("teste", true)
                .withEqualFilter("teste2", null)
                .withEqualFilter("teste3", "testando")
                .buildList().size();
        assertThat(count, equalTo(2));
    }

    @Test
    public void should_return_a_specification_not_null() {
        Specification<Object> objectSpecification = SpecificationBuilder.init()
                .withEqualFilter("teste", true)
                .withEqualFilter("teste2", "teste")
                .buildSpec();
        assertNotNull(objectSpecification);
    }

}