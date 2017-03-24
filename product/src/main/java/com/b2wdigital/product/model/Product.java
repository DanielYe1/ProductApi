package com.b2wdigital.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Created by daniel.ye on 14/03/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String image;

    public com.b2wdigital.product.controller.api.Product toProductApi() {
        return new com.b2wdigital.product.controller.api.Product(id, name, image);
    }

}
