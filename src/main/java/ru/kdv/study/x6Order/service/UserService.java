package ru.kdv.study.x6Order.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kdv.study.x6Order.config.UserServiceProperties;

@Service
@AllArgsConstructor
public class UserService {

    private RestTemplate restTemplate;
    private UserServiceProperties userServiceConfig;

    private static final String URL_USER_EXIST = "/user/exist/%d";

    public boolean existUser(final Long id){
        return Boolean.TRUE.equals(restTemplate.getForObject(buildFullUrl(String.format(URL_USER_EXIST, id)), Boolean.class, id));
    }

    private String buildFullUrl(final String localUrl) {
        return userServiceConfig.getBaseUrl() + localUrl;
    }
}