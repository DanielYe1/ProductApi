package com.b2wdigital.product.repository;

import com.b2wdigital.product.controller.api.FilterMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SortBuilderTest {

    @InjectMocks
    private SortBuilder sortBuilder;

    @Mock
    private OrderBuilder orderBuilder;

    @Test
    public void deveria_criar_sort_com_lista_de_orders() {
        FilterMetadata filterMetadata = mock(FilterMetadata.class);
        String param = "name.asc";
        Sort.Order order = mock(Sort.Order.class);

        when(filterMetadata.getSortBy()).thenReturn(Collections.singletonList(param));
        when(orderBuilder.build(param)).thenReturn(order);

        assertThat(sortBuilder.build(filterMetadata), equalTo(new Sort(Collections.singletonList(order))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveria_lancar_excecao_de_argumento_invalido_devido_a_sort_nula_por_parametro_vazio() {
        FilterMetadata filterMetadata = mock(FilterMetadata.class);

        when(filterMetadata.getSortBy()).thenReturn(Collections.emptyList());

        assertThat(sortBuilder.build(filterMetadata), equalTo(new Sort(Collections.emptyList())));
        verify(orderBuilder, never()).build(any());
    }

}