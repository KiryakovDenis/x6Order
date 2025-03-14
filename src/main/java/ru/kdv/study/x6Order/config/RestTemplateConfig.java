package ru.kdv.study.x6Order.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.kdv.study.x6Order.logging.RestTemplateLogger;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private static final Long READ_TIMEOUT = 10000L;
    private static final Long CONNECTION_TIMEOUT = 10000L;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
                .readTimeout(Duration.ofMillis(READ_TIMEOUT))
                .connectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                .interceptors(new RestTemplateLogger())
                .build();
    }
}