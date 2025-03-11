package ru.kdv.study.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="product-service")
@Getter
@Setter
public class ProductServiceConfig {
    private String baseUrl;
    private Long readTimeout;
    private Long connectionTimeout;

}
