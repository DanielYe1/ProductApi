package com.b2wdigital.product.controller.api;

import com.b2wdigital.product.repository.OrderBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class FilterMetadata {
    private int offset = 0;
    private int limit = 20;
    private List<String> sortBy;
    private List<String> fields;

    public boolean hasSortBy() {
        return sortBy != null && !sortBy.isEmpty();
    }

    public boolean hasFields() {
        return fields != null && !fields.isEmpty();
    }
}
