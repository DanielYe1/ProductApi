package com.b2wdigital.product.controller.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FilterMetadata {
    private int offSet;
    private int limit;
    private List<String> sortBy;
    private List<String> fields;
}
