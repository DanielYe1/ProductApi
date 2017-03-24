package com.b2wdigital.product.repository;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.Product;
import com.b2wdigital.product.repository.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderTest {

    @InjectMocks
    private QueryBuilder creator;

    @Mock
    private SortBuilder sortBuilder;

    @Test
    public void deveria_criar_query_com_todos_os_atributos() {
        Product product = new Product("1", "nome", "imagem");
        FilterMetadata filterMetadata = new FilterMetadata();

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("nome"));
        query.addCriteria(Criteria.where("image").is("imagem"));

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_apenas_com_atributo_nome() {
        Product product = new Product();
        product.setImage("imagem");
        FilterMetadata filterMetadata = new FilterMetadata();

        Query query = new Query();
        query.addCriteria(Criteria.where("image").is("imagem"));

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_apenas_com_atributo_imagem() {
        Product product = new Product();
        product.setName("nome");
        FilterMetadata filterMetadata = new FilterMetadata();

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("nome"));

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_com_limite_de_objetos_de_retorno() {
        Product product = new Product();
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setLimit(10);

        Query query = new Query();
        query.limit(filterMetadata.getLimit());

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_com_offset() {
        Product product = new Product();
        FilterMetadata filterMetadata = new FilterMetadata();
        filterMetadata.setOffset(2);

        Query query = new Query();
        query.skip(2);

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }
}