package com.b2wdigital.product.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Created by daniel.ye on 16/03/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private String image;

    public com.b2wdigital.product.model.Product toProductModel() {
        return new com.b2wdigital.product.model.Product(id, name, image);
    }
}
