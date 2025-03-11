package ru.kdv.study.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.kdv.study.logging.RestTemplateLogger;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final UserServiceConfig userServiceConfig;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
                .readTimeout(Duration.ofMillis(userServiceConfig.getReadTimeout()))
                .connectTimeout(Duration.ofMillis(userServiceConfig.getConnectionTimeout()))
                .interceptors(new RestTemplateLogger())
                .build();
    }
}