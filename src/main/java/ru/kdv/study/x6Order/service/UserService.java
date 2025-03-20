package ru.kdv.study.x6Order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kdv.study.x6Order.Exception.ExternalServiceException;
import ru.kdv.study.x6Order.config.UserServiceProperties;

import java.util.StringJoiner;

@Service
@AllArgsConstructor
public class UserService {

    private RestTemplate restTemplate;
    private UserServiceProperties userServiceConfig;

    private static final String URL_USER_EXIST = "/user/exist/%d";

    private static final String URL_GET_USER_BY_ID = "/user/%d";

    public boolean existUser(final Long id){
        return Boolean.TRUE.equals(restTemplate.getForObject(buildFullUrl(String.format(URL_USER_EXIST, id)), Boolean.class, id));
    }

    private String buildFullUrl(final String localUrl) {
        try {
            return userServiceConfig.getBaseUrl() + localUrl;
        } catch (Exception e) {
            throw handleServiceException(e);
        }
    }

    private ExternalServiceException handleServiceException(Exception e) {
        return ExternalServiceException.create(new StringJoiner("/n")
                .add("UserService")
                .add(e.getMessage())
                .add(e.getCause().toString())
                .toString());
    }
}