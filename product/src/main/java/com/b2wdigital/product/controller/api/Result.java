package com.b2wdigital.product.controller.api;


import lombok.*;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private long total;
    private int limit;
    private int offset;
}
