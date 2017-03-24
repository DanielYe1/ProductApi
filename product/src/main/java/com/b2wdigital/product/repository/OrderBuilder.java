package com.b2wdigital.product.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderBuilder {

    public Sort.Order build(String param) {
        String[] filter = param.split("\\.", 2);
        if(param.matches("\\w+\\.(asc|desc)$")){
            return new Sort.Order(Optional.ofNullable(filter[1]).map(String::toUpperCase).map(Sort.Direction::valueOf).orElse(Sort.Direction.ASC), filter[0]);
        } else if (param.matches("\\w+$")) {
            return new Sort.Order(Sort.Direction.ASC, filter[0]);
        } else {
            throw new IllegalArgumentException("Nao reconhecido parametro de direcao");
        }
    }
}
