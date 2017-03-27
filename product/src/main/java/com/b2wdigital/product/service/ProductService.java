package com.b2wdigital.product.service;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.Product;
import com.b2wdigital.product.controller.api.ProductList;
import com.b2wdigital.product.controller.api.Result;
import com.b2wdigital.product.repository.ProductRepository;
import com.b2wdigital.product.repository.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private QueryBuilder queryBuilder;

    public ProductList findAllBy(Product filter, FilterMetadata filterMetadata) {
        Query query = queryBuilder.buildFilter(filter, filterMetadata);

        return new ProductList(mongo.find(query, com.b2wdigital.product.model.Product.class).stream().map(com.b2wdigital.product.model.Product::toProductApi).collect(Collectors.toList()),
                new Result(mongo.count(query, com.b2wdigital.product.model.Product.class)));
    }

    public Product add(Product product) {
        return repository.insert(product.toProductModel()).toProductApi();
    }

    public boolean delete(String id) {
        boolean exists = repository.exists(id);
        if (exists) {
            repository.delete(id);
        }
        return exists;
    }

    public boolean update(String id, Product product) {
        boolean exists = repository.exists(id);
        if (exists) {
            product.setId(id);
            repository.save(product.toProductModel());
        }
        return exists;
    }

    public Optional<Product> findProduct(String id) {
        return Optional.ofNullable(repository.findOne(id)).map(com.b2wdigital.product.model.Product::toProductApi);
    }
}