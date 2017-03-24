package com.b2wdigital.product.controller.api;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponseHeaderBuilderTest {


    @Test
    public void deveria_adicionar_location_na_header() {
        ResponseHeaderBuilder headerBuilder = new ResponseHeaderBuilder();
        Set<Map.Entry<String, List<String>>> headers = headerBuilder.buildLocation("1").entrySet();

        assertThat(
                headers,
                hasItem(
                        both(hasProperty("key", equalTo("Location")))
                                .and(hasProperty("value", equalTo(Arrays.asList("/product/1"))))
                )
        );
        assertThat(headers, hasSize(1));
    }


}