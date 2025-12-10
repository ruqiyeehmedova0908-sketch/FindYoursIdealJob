package com.example.findyoursidealjob.dao.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "companies")

public class Companies {
    public Companies(Long id) {
        this.id = id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer employeeCount;

    private String icon;

    @Column(name = "contact_email")
    private String contactEmail;


    @OneToMany(mappedBy = "companies", fetch = FetchType.LAZY)
    private List<Vacancies> vacancies;
}
