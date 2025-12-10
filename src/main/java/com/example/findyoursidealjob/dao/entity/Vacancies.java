package com.example.findyoursidealjob.dao.entity;

import com.example.findyoursidealjob.enums.DegreeOfDuty;
import com.example.findyoursidealjob.enums.EmploymentType;
import com.example.findyoursidealjob.enums.Location;
import com.example.findyoursidealjob.enums.OrderBy;
import com.example.findyoursidealjob.enums.Salary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancies")

public class Vacancies {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // Elanın başlığı
        @Column(nullable = false)
        private String title;

        @ManyToOne
        @JoinColumn(name = "companies_id")
        private Companies companies;

        @ManyToOne
        @JoinColumn(name = "industries_id")
        private Industries industries;

        @ManyToOne
        @JoinColumn(name = "categories_id")
        private Categories categories;

        @OneToOne(mappedBy = "vacancies", cascade = CascadeType.ALL,orphanRemoval = true)
        private VacanciesDetail vacanciesDetail;

        // Elan təsviri
        @Column(columnDefinition = "TEXT")
        private String description;

        @Column(name = "salary_m")
        private Integer salaryM;

        // Tələblər
        @Column(columnDefinition = "TEXT")
        private String requirements;

        // Maaş enum
        @Enumerated(EnumType.STRING)
        private Salary salary;

        // İş rejimi
        @Enumerated(EnumType.STRING)
        private EmploymentType employmentType;

        @Enumerated(EnumType.STRING)
         private Location location;

        @Enumerated(EnumType.STRING)
        private DegreeOfDuty degreeOfDuty;

        @Enumerated(EnumType.STRING)
        private OrderBy orderBy;

        private String city;
        private String address;

        private LocalDate publishedDate;
        private LocalDate deadline;

        private boolean isNew;



}
