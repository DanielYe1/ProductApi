package com.b2wdigital.product.repository;

import com.b2wdigital.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by daniel.ye on 15/03/17.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
}
