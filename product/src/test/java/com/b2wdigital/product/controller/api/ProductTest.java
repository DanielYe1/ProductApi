package com.b2wdigital.product.controller.api;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void deveria_criar_produto_corretamente() {
        Product product = new Product("1", "name", "img");
        assertThat(product.toProductModel(), equalTo(new com.b2wdigital.product.model.Product("1", "name", "img")));
    }
}