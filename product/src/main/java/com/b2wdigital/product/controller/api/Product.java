package com.b2wdigital.product.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    private String id;
    private String name;
    private String image;

    public com.b2wdigital.product.model.Product toProductModel() {
        return new com.b2wdigital.product.model.Product(id, name, image);
    }
}
