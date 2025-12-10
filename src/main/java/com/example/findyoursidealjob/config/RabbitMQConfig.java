package com.example.findyoursidealjob.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String VACANCY_QUEUE = "vacancy.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String EMAIL_QUEUE = "email.queue";

    public static final String EXCHANGE = "job.exchange";

    public static final String VACANCY_ROUTING_KEY = "vacancy.created";
    public static final String NOTIFICATION_ROUTING_KEY = "notification.send";
    public static final String EMAIL_ROUTING_KEY = "email.send";

    @Bean
    public Queue vacancyQueue() {
        return new Queue(VACANCY_QUEUE, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding vacancyBinding(Queue vacancyQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(vacancyQueue)
                .to(exchange)
                .with(VACANCY_ROUTING_KEY);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(exchange)
                .with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(exchange)
                .with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
