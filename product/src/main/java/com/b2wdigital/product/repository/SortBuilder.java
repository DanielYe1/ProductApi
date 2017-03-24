package com.b2wdigital.product.repository;

import com.b2wdigital.product.controller.api.FilterMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortBuilder {
    @Autowired
    private OrderBuilder orderBuilder;

    public Sort build(FilterMetadata filterMetadata) {
        if (filterMetadata.getSortBy() == null) {
            throw new IllegalArgumentException("sortBy obrigatorio");
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (String param : filterMetadata.getSortBy()) {
            orders.add(orderBuilder.build(param));
        }
        return new Sort(orders);
    }
}
