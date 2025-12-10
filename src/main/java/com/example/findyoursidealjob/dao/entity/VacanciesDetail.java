package com.example.findyoursidealjob.dao.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vacancies_detail")
public class VacanciesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isNew;

    private LocalDate deadline;

    private LocalDate publishedDate;

    @OneToOne
    @JoinColumn(name = "vacancies_id", referencedColumnName = "id")
    private Vacancies vacancies;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    private int monthlyViews;

    @Column(name = "contact_email")
    private String contactEmail;
}
