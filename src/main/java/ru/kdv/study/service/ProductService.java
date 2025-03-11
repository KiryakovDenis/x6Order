package ru.kdv.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kdv.study.config.ProductServiceConfig;
import ru.kdv.study.Exception.ExternalServiceException;
import ru.kdv.study.model.ProductExist;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final RestTemplate restTemplate;
    private final ProductServiceConfig productServiceConfig;

    private static final String PRODUCT_EXIST_URL = "/product/exist";

    public List<ProductExist> productExist(final List<Long> ids) {
        ProductExist[] productExists;
        try {
            productExists = restTemplate.postForObject(buildProductUrl(PRODUCT_EXIST_URL), ids, ProductExist[].class);
        } catch (Exception e) {
            throw ExternalServiceException.create(new StringJoiner("/n")
                    .add("ProductService")
                    .add(e.getMessage())
                    .add(e.getCause().toString())
                    .toString());
        }

        if (productExists != null ) {
            return Arrays.stream(productExists).toList();
        } else {
            return null;
        }
    }

    private String buildProductUrl(final String endpoint) {
        return productServiceConfig.getBaseUrl() + endpoint;
    }
}
