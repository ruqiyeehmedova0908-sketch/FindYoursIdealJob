package com.example.findyoursidealjob.messaging;

import com.example.findyoursidealjob.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void consumeNotification(String message) {
        log.info("Bildiriş alındı: {}", message);
        

    }
}
