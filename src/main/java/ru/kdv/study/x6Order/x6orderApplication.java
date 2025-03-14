package ru.kdv.study.x6Order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import ru.kdv.study.x6Order.config.UserServiceProperties;
import ru.kdv.study.x6Order.service.ProductService;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({UserServiceProperties.class, ProductService.class})
public class x6orderApplication {
    public static void main(String[] args) {
        SpringApplication.run(x6orderApplication.class, args);
    }
}