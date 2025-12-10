package com.example.findyoursidealjob.messaging;

import com.example.findyoursidealjob.config.RabbitMQConfig;
import com.example.findyoursidealjob.dto.VacanciesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VacancyConsumer {

    @RabbitListener(queues = RabbitMQConfig.VACANCY_QUEUE)
    public void consumeVacancyMessage(VacanciesDto vacancy) {
        log.info("Yeni vakansiya alındı: {}", vacancy.getTitle());
        log.info("Şirkət: {}", vacancy.getCompaniesId());
        log.info("Maaş: {} AZN", vacancy.getSalaryM());
        

    }
}
