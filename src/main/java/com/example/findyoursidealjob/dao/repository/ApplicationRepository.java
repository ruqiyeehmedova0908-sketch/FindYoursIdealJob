package com.example.findyoursidealjob.dao.repository;

import com.example.findyoursidealjob.dao.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByVacanciesId(Long vacanciesId);
    List<Application> findByApplicantEmail(String applicantEmail);
}
