package ru.kdv.study.x6Order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="user-service")
@Getter
@Setter
public class UserServiceProperties {
    private String baseUrl;
}