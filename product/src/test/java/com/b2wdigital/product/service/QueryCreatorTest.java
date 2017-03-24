package com.b2wdigital.product.service;

import com.b2wdigital.product.controller.api.Product;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class QueryCreatorTest {

    QueryCreator creator = new QueryCreator();

    @Test
    public void deveria_criar_query_com_todos_os_atributos() {
        Product product = new Product("1", "nome", "imagem");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("nome"));
        query.addCriteria(Criteria.where("image").is("imagem"));

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_apenas_com_atributo_nome() {
        Product product = new Product();
        product.setImage("imagem");

        Query query = new Query();
        query.addCriteria(Criteria.where("image").is("imagem"));

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_apenas_com_atributo_imagem() {
        Product product = new Product();
        product.setName("nome");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("nome"));

        assertThat(creator.buildFilter(product, filterMetadata), equalTo(query));
    }

    @Test
    public void deveria_criar_query_sem_atributos(){
        Product product = new Product();

    }
}