package com.b2wdigital.product.service;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.Product;
import com.b2wdigital.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private QueryCreator queryCreator;

    public List<Product> findAllBy(Product filter, FilterMetadata filterMetadata) {
        return mongo.find(queryCreator.buildFilter(filter,filterMetadata), com.b2wdigital.product.model.Product.class).stream().map(com.b2wdigital.product.model.Product::toProductApi).collect(Collectors.toList());
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