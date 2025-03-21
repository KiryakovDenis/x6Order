package ru.kdv.study.x6Order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kdv.study.x6Order.config.ProductServiceProperties;
import ru.kdv.study.x6Order.Exception.ExternalServiceException;
import ru.kdv.study.x6Order.model.ProductExist;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final RestTemplate restTemplate;
    private final ProductServiceProperties productServiceProperties;

    private static final String PRODUCT_EXIST_URL = "/product/exist";

    public List<ProductExist> productExist(final List<Long> ids) {
        ProductExist[] productExists;
        try {
            productExists = restTemplate.postForObject(buildProductUrl(), ids, ProductExist[].class);
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

    private String buildProductUrl() {
        return productServiceProperties.getBaseUrl() + PRODUCT_EXIST_URL;
    }
}