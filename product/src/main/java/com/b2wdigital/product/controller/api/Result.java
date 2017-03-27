package com.b2wdigital.product.controller.api;


import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class Result {
    private long total;
    private int limit=20;
    private int offset=0;

    public Result(long total) {
        this.total = total;
    }
}
