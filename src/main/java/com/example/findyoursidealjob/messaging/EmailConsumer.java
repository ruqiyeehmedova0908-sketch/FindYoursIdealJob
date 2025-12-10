package com.example.findyoursidealjob.messaging;

import com.example.findyoursidealjob.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumer {

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeEmailMessage(MessageProducer.EmailMessage emailMessage) {
        log.info("Email mesajı alındı:");
        log.info("Alıcı: {}", emailMessage.to());
        log.info("Mövzu: {}", emailMessage.subject());
        log.info("Mətn: {}", emailMessage.body());
        

    }
}
