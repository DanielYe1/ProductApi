package com.b2wdigital.product.repository;

import com.b2wdigital.product.controller.api.FilterMetadata;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FieldFilter {

    public void includeFields(Query query, FilterMetadata filterMetadata) {
        List<String> filteredFields = filterMetadata.getFields().stream().filter(s -> !s.equals("")).collect(Collectors.toList());
        for (String field : filteredFields) {
            query.fields().include(field);
        }
    }
}
