package ru.kdv.study.x6Order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {
    private final RabbitConfigurationProperties rabbitConfigurationProperties;

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue x6NotificationQueue() {
        return new Queue(rabbitConfigurationProperties.getQueue(), true);
    }

    @Bean
    public DirectExchange x6NotificationExchange() {
        return new DirectExchange(rabbitConfigurationProperties.getExchange());
    }

    @Bean
    public Binding x6NotificationBinding(final Queue x6NotificationQueue, final DirectExchange x6NotificationExchange) {
        return BindingBuilder
                .bind(x6NotificationQueue)
                .to(x6NotificationExchange)
                .withQueueName();
    }
}
