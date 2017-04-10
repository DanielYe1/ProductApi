package com.b2wdigital.product.controller;

import com.b2wdigital.product.controller.api.*;
import com.b2wdigital.product.repository.ProductRepository;
import com.b2wdigital.product.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @MockBean
    private ResponseHeaderBuilder headerBuilder;

    @MockBean
    private FilterMetadata filterMetadata;

    @Test
    public void deveria_retornar_dados_do_produto() throws Exception {
        given(service.findAllBy(any(Product.class), any(FilterMetadata.class))).willReturn(new ProductList(Collections.singletonList(new Product("1", "prod1", "img1")),
                new Result(20,20,0)));

        this.mvc.
                perform(get("/product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"products\":[{\"id\":\"1\",\"name\":\"prod1\",\"image\":\"img1\"}],\"_result\":{\"total\":20,\"limit\":20,\"offset\":0}}")).andDo(print());
    }

    @Test
    public void deveria_adicionar_novo_produto() throws Exception {

        Product productApi = mock(Product.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/product/1");

        given(service.add(any(Product.class))).willReturn(productApi);
        given(productApi.getId()).willReturn("1");
        given(headerBuilder.buildLocation("1")).willReturn(headers);

        mvc.perform(post("/product")
                .content("{\"name\":\"prod1\",\"image\":\"img1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/product/1"));
    }

    @Test
    public void deveria_deletar_produto_por_id() throws Exception {
        Product productApi = mock(Product.class);
        String id = "1";
        given(service.delete(id)).willReturn(true);

        mvc.perform(delete("/product/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void nao_deveria_deletar_produto_nao_encontrado() throws Exception {
        Product productApi = mock(Product.class);
        String id = "1";
        given(service.delete(id)).willReturn(false);

        mvc.perform(delete("/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveria_atualizar_produto() throws Exception {
        String id = "1";
        given(service.update(eq(id), any(Product.class))).willReturn(true);

        mvc.perform(put("/product/1")
                .content("{\"name\":\"prod1\",\"image\":\"img1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void nao_deveria_atualizar_produto_nao_encontrado() throws Exception {
        String id = "1";
        given(service.update(eq(id), any(Product.class))).willReturn(false);

        mvc.perform(put("/product/1")
                .content("{\"name\":\"prod1\",\"image\":\"img1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveria_retornar_produto_pelo_id_dado() throws Exception {
        String id = "1";
        Product product = new Product("1", "prod1", "img1");

        given(service.findProduct(id)).willReturn(Optional.of(product));

        mvc.perform(get("/product/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":\"1\",\"name\":\"prod1\",\"image\":\"img1\"}"));
    }

    @Test
    public void nao_deveria_retornar_produto_com_id_nao_encontrado() throws Exception {
        String id = "1";

        given(service.findProduct(id)).willReturn(Optional.empty());

        mvc.perform(get("/product/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}