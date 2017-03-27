package com.b2wdigital.product.service;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.ProductList;
import com.b2wdigital.product.controller.api.Result;
import com.b2wdigital.product.model.Product;
import com.b2wdigital.product.repository.ProductRepository;
import com.b2wdigital.product.repository.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService service;

    @Mock
    ProductRepository repository;

    @Mock
    MongoTemplate mongo;

    @Mock
    QueryBuilder queryBuilder;

    @Test
    public void deveria_retornar_todos_produtos() {
        FilterMetadata filterMetadata = mock(FilterMetadata.class);
        Product product = mock(Product.class);
        Query query = mock(Query.class);
        long total = 1;
        com.b2wdigital.product.controller.api.Product productApi = mock(com.b2wdigital.product.controller.api.Product.class);

        when(queryBuilder.buildFilter(productApi, filterMetadata)).thenReturn(query);
        when(mongo.find(query, Product.class)).thenReturn(Collections.singletonList(product));
        when(product.toProductApi()).thenReturn(productApi);
        when(mongo.count(query, Product.class)).thenReturn(total);
        assertThat(service.findAllBy(productApi, filterMetadata), equalTo(new ProductList(Collections.singletonList(productApi), new Result(total))));
    }

    @Test
    public void deveria_adicionar_produto() {
        com.b2wdigital.product.controller.api.Product productApi = mock(com.b2wdigital.product.controller.api.Product.class);
        Product product = mock(Product.class);
        when(productApi.toProductModel()).thenReturn(product);
        when(repository.insert(product)).thenReturn(product);
        when(product.toProductApi()).thenReturn(productApi);

        assertThat(service.add(productApi), equalTo(productApi));
        verify(repository).insert(product);
        verify(product).toProductApi();
        verify(productApi).toProductModel();
    }

    @Test
    public void deveria_retirar_produto() {
        doNothing().when(repository).delete("1");
        when(repository.exists(eq("1"))).thenReturn(true);

        assertThat(service.delete("1"), equalTo(true));

        verify(repository).delete("1");
    }

    @Test
    public void nao_deveria_retirar_produto_inexistente() {
        when(repository.exists(eq("1"))).thenReturn(false);

        assertThat(service.delete("1"), equalTo(false));

        verify(repository, never()).delete("1");
    }

    @Test
    public void deveria_atualizar_produto() {
        String id = "1";
        com.b2wdigital.product.controller.api.Product productApi = mock(com.b2wdigital.product.controller.api.Product.class);
        Product product = mock(Product.class);

        when(repository.exists("1")).thenReturn(true);
        doNothing().when(productApi).setId("1");
        when(productApi.toProductModel()).thenReturn(product);

        assertThat(service.update(id, productApi), equalTo(true));

        verify(productApi).setId("1");
        verify(productApi).toProductModel();
        verify(repository).save(product);
    }

    @Test
    public void nao_deveria_atualizar_produto_nao_encontrado() {
        String id = "1";
        com.b2wdigital.product.controller.api.Product productApi = mock(com.b2wdigital.product.controller.api.Product.class);

        when(repository.exists(id)).thenReturn(false);

        assertThat(service.update(id, productApi), equalTo(false));

        verify(productApi, never()).setId(id);
        verify(productApi, never()).toProductModel();
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    public void deveria_retornar_optional_de_produto_pelo_id_dado() {
        String id = "1";
        com.b2wdigital.product.controller.api.Product productApi = mock(com.b2wdigital.product.controller.api.Product.class);
        Product product = mock(Product.class);

        when(repository.findOne(id)).thenReturn(product);
        when(product.toProductApi()).thenReturn(productApi);

        assertThat(service.findProduct(id), equalTo(Optional.ofNullable(productApi)));

        verify(repository).findOne(id);
        verify(product).toProductApi();
    }

    @Test
    public void deveria_retornar_optional_nulo_por_id_inexistente() {
        String id = "1";
        when(repository.findOne(id)).thenReturn(null);

        assertThat(service.findProduct(id), equalTo(Optional.empty()));

        verify(repository).findOne(id);
    }
}