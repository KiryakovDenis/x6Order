package ru.kdv.study.x6Order.Exception;

public class ExternalServiceException extends RuntimeException{

    public static ExternalServiceException create(String message) {
        return new ExternalServiceException(message);
    }

    public ExternalServiceException(String message) {
        super(message);
    }
}