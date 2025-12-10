package com.example.findyoursidealjob.messaging;

import com.example.findyoursidealjob.config.RabbitMQConfig;
import com.example.findyoursidealjob.dto.VacanciesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendVacancyCreatedMessage(VacanciesDto vacancy) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.VACANCY_ROUTING_KEY,
                    vacancy
            );
            log.info("Vakansiya mesajı göndərildi: {}", vacancy.getTitle());
        } catch (Exception e) {
            log.error("Mesaj göndərilərkən xəta: ", e);
        }
    }

    public void sendNotification(String message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.NOTIFICATION_ROUTING_KEY,
                    message
            );
            log.info("Bildiriş göndərildi: {}", message);
        } catch (Exception e) {
            log.error("Bildiriş göndərilərkən xəta: ", e);
        }
    }

    public void sendEmail(String email, String subject, String body) {
        try {
            EmailMessage emailMessage = new EmailMessage(email, subject, body);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.EMAIL_ROUTING_KEY,
                    emailMessage
            );
            log.info("Email mesajı göndərildi: {}", email);
        } catch (Exception e) {
            log.error("Email göndərilərkən xəta: ", e);
        }
    }

    // Email mesaj strukturu
    public record EmailMessage(String to, String subject, String body) {}
}
