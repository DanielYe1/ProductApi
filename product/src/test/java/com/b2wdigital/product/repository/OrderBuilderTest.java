package com.b2wdigital.product.repository;

import com.b2wdigital.product.repository.OrderBuilder;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class OrderBuilderTest {

    @Test
    public void deveria_criar_order_corretamente_com_direcao_padrao() {
        OrderBuilder orderBuilder = new OrderBuilder();

        assertThat(orderBuilder.build("name"), equalTo(new Sort.Order(Sort.Direction.ASC, "name")));
    }

    @Test
    public void deveria_criar_order_ascendente_corretamente() {
        OrderBuilder orderBuilder = new OrderBuilder();

        assertThat(orderBuilder.build("name.asc"), equalTo(new Sort.Order(Sort.Direction.ASC, "name")));
    }

    @Test
    public void deveria_criar_order_descendente_corretamente() {
        OrderBuilder orderBuilder = new OrderBuilder();

        assertThat(orderBuilder.build("name.desc"), equalTo(new Sort.Order(Sort.Direction.DESC, "name")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveria_lancar_excecao_por_argumento_invalido_ao_criar_order() {
        OrderBuilder orderBuilder = new OrderBuilder();

        orderBuilder.build("name.ddd");
    }
}