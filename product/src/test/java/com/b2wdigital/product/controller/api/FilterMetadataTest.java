package com.b2wdigital.product.controller.api;

import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FilterMetadataTest {

    @Test
    public void deveria_retornar_verdadeiro_por_ter_sortby() {
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setSortBy(Collections.singletonList("name.asc"));
        assertThat(filterMetadata.hasSortBy(), equalTo(true));
    }

    @Test
    public void deveria_retornar_falso_por_ter_sortby_nulo() {
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setSortBy(null);
        assertThat(filterMetadata.hasSortBy(), equalTo(false));
    }

    @Test
    public void deveria_retornar_falso_por_ter_sortby_vazio() {
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setSortBy(Collections.emptyList());
        assertThat(filterMetadata.hasSortBy(), equalTo(false));
    }

    @Test
    public void deveria_retornar_verdadeiro_por_ter_fields() {
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setFields(Collections.singletonList("name"));
        assertThat(filterMetadata.hasFields(), equalTo(true));
    }

    @Test
    public void deveria_retornar_falso_por_ter_fields_nulo() {
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setFields(null);
        assertThat(filterMetadata.hasFields(), equalTo(false));
    }

    @Test
    public void deveria_retornar_falso_por_ter_fields_vazio() {
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setFields(Collections.emptyList());
        assertThat(filterMetadata.hasFields(), equalTo(false));
    }
}