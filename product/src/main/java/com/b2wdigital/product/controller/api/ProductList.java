package com.b2wdigital.product.controller.api;

import com.b2wdigital.product.controller.api.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
public class ProductList {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<Product> products;

    public ProductList(List<Product> products) {
        this.products = products;
    }
}
