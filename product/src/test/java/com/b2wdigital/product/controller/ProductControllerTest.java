package com.b2wdigital.product.controller;

import com.b2wdigital.product.controller.api.*;
import com.b2wdigital.product.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductService service;

    @Mock
    private ResponseHeaderBuilder headerBuilder;

    @Mock
    private FilterMetadata filterMetadata;

    @Test
    public void deveria_retornar_todos_os_produtos() {
        Product product = mock(Product.class);
        Result result = mock(Result.class);
        ProductList productList = new ProductList(Collections.singletonList(product), result);

        when(service.findAllBy(product, filterMetadata)).thenReturn(productList);

        assertThat(controller.list(product, filterMetadata), equalTo(productList));
        verify(service).findAllBy(eq(product), eq(filterMetadata));
    }

    @Test
    public void deveria_adicionar_produtos() {
        final Product product = mock(Product.class);
        when(service.add(product)).thenReturn(product);
        when(product.getId()).thenReturn("1");
        HttpHeaders headers = mock(HttpHeaders.class);
        when(headerBuilder.buildLocation("1")).thenReturn(headers);
        assertThat(controller.add(product), equalTo(new ResponseEntity(headers, HttpStatus.CREATED)));
    }

    @Test
    public void deveria_deletar_produto_por_id() {
        when(service.delete("1")).thenReturn(true);
        assertThat(controller.delete("1"), equalTo(new ResponseEntity(HttpStatus.NO_CONTENT)));
        verify(service).delete("1");
    }

    @Test
    public void nao_deveria_deletar_produto_nao_identificado() {
        when(service.delete("1")).thenReturn(false);
        assertThat(controller.delete("1"), equalTo(new ResponseEntity(HttpStatus.NOT_FOUND)));
        verify(service).delete("1");
    }

    @Test
    public void deveria_atualizar_produto() {
        String id = "1";
        Product product = mock(Product.class);
        when(service.update(id, product)).thenReturn(true);

        assertThat(controller.update(id, product), equalTo(new ResponseEntity(HttpStatus.NO_CONTENT)));

        verify(service).update(id, product);
    }

    @Test
    public void nao_deveria_atualizar_produto_nao_encontrado() {
        String id = "1";
        Product product = mock(Product.class);
        when(service.update(id, product)).thenReturn(false);

        assertThat(controller.update(id, product), equalTo(new ResponseEntity(HttpStatus.NOT_FOUND)));

        verify(service).update(id, product);
    }

    @Test
    public void deveria_retornar_produto_pelo_id() {
        String id = "1";
        Product product = mock(Product.class);
        when(service.findProduct(id)).thenReturn(Optional.ofNullable(product));

        assertThat(controller.search(id), equalTo(new ResponseEntity<Product>(product, HttpStatus.OK)));
    }

    @Test
    public void deveria_retornar_nulo_por_id_inexistente() {
        String id = "1";
        Product product = mock(Product.class);

        when(service.findProduct(id)).thenReturn(Optional.empty());

        assertThat(controller.search(id), equalTo(new ResponseEntity<Product>(HttpStatus.NOT_FOUND)));
    }
}