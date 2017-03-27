package com.b2wdigital.product.repository;

import com.b2wdigital.product.controller.api.FilterMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FieldFilterTest {

    @InjectMocks
    private FieldFilter fieldFilter;

    @Mock
    private Query query;

    @Mock
    private FilterMetadata filterMetadata;

    @Mock
    private Field field;

    @Test
    public void deveria_adicionar_lista_de_campos_na_query() {
        List<String> fields = Arrays.asList("name");
        when(query.fields()).thenReturn(field);
        when(field.include("name")).thenReturn(field);
        when(filterMetadata.getFields()).thenReturn(fields);

        fieldFilter.includeFields(query, filterMetadata);

        verify(query).fields();
        verify(field).include("name");
    }

    @Test
    public void nao_deveria_adicionar_nada_na_query_por_lista_de_campos_vazia() {
        List<String> fields = Collections.emptyList();
        when(filterMetadata.getFields()).thenReturn(fields);

        fieldFilter.includeFields(query, filterMetadata);

        verify(query, never()).fields();
        verify(field, never()).include(anyString());
    }

    @Test
    public void deveria_ignorar_valores_vazios_na_query() {
        List<String> fields = Arrays.asList("", "", "name");
        when(query.fields()).thenReturn(field);
        when(field.include("name")).thenReturn(field);
        when(filterMetadata.getFields()).thenReturn(fields);

        fieldFilter.includeFields(query, filterMetadata);

        verify(query, only()).fields();
        verify(field).include("name");
    }

}