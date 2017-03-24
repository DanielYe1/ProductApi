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
    private int offset;
    private int limit;
    private List<String> sortBy;
    private List<String> fields;

    public boolean hasSortBy() {
        return sortBy != null && !sortBy.isEmpty();
    }
}
