package com.b2wdigital.product.repository;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryBuilder {
    @Autowired
    private SortBuilder sortBuilder;

    public Query buildFilter(Product filter, FilterMetadata filterMetadata) {
        Query query = new Query();
        Optional.ofNullable(filter.getName()).ifPresent(s -> query.addCriteria(Criteria.where("name").is(s)));
        Optional.ofNullable(filter.getImage()).ifPresent(img -> query.addCriteria(Criteria.where("image").is(img)));

        query.skip(filterMetadata.getOffset());
        query.limit(filterMetadata.getLimit());
        if (filterMetadata.hasSortBy()) {
            query.with(sortBuilder.build(filterMetadata));
        }
        return query;
    }
}