package com.b2wdigital.product.controller.api;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class ResponseHeaderBuilder {

    public HttpHeaders buildLocation(String id) {
        HttpHeaders header = new HttpHeaders();
        header.add("Location", String.format("/product/%s", id));
        return header;
    }
}
