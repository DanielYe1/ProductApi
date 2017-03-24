package com.b2wdigital.product.service;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.Product;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryCreator {

    public Query buildFilter(Product filter, FilterMetadata filterMetadata) {
        Query query = new Query();
        Optional.ofNullable(filter.getName()).ifPresent(s -> query.addCriteria(Criteria.where("name").is(s)));
        Optional.ofNullable(filter.getImage()).ifPresent(img -> query.addCriteria(Criteria.where("image").is(img)));
        query.limit(filterMetadata.getLimit());
        return query;
    }
}