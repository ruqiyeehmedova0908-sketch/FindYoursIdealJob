package com.example.findyoursidealjob.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vacancies_id", nullable = false)
    private Vacancies vacancies;

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private String applicantEmail;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Column(nullable = false)
    private String resumeUrl;

    @Column(nullable = false)
    private LocalDateTime appliedDate;

    @Column(nullable = false)
    private String status; // PENDING, REVIEWED, ACCEPTED, REJECTED

    private LocalDateTime reviewedDate;

    private String reviewNotes;

    @Column(name = "application_location")
    private String applicationLocation;
}
