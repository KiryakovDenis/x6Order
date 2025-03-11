package ru.kdv.study.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="user-service")
@Getter
@Setter
public class UserServiceConfig {
    private String baseUrl;
    private Long readTimeout;
    private Long connectionTimeout;

}
