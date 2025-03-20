package ru.kdv.study.x6Order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.kdv.study.x6Order.config.RabbitConfigurationProperties;
import ru.kdv.study.x6Order.model.Order;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitConfigurationProperties rabbitConfigurationProperties;

    public void sendMessage(Order order) {
        rabbitTemplate.convertAndSend(rabbitConfigurationProperties.getExchange(),
                rabbitConfigurationProperties.getQueue(),
                order
        );
    }
}
