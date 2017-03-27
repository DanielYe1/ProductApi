package com.b2wdigital.product.controller;

import com.b2wdigital.product.controller.api.FilterMetadata;
import com.b2wdigital.product.controller.api.Product;
import com.b2wdigital.product.controller.api.ProductList;
import com.b2wdigital.product.controller.api.ResponseHeaderBuilder;
import com.b2wdigital.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ResponseHeaderBuilder headerBuilder;

    public ProductController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ProductList list(Product filter, FilterMetadata filterMetadata) {
        return service.findAllBy(filter, filterMetadata);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> search(@PathVariable("id") String id) {
        Optional<Product> product = service.findProduct(id);
        if (product.isPresent()) {
            return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Product product) {
        Product added = service.add(product);
        return new ResponseEntity(headerBuilder.buildLocation(added.getId()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        if (service.delete(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody Product product) {
        if (service.update(id, product)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
