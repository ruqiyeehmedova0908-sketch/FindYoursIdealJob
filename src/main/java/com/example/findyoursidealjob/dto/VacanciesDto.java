package com.example.findyoursidealjob.dto;

import com.example.findyoursidealjob.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacanciesDto {
    private Long id;
    private String title;
    private Long companiesId;
    private Long industriesId;
    private Long categoriesId;
    private String companyName;
    private String categoryName;
    private String industryName;
    private String description;
    private Integer salaryM;
    private String requirements;
    private Salary salary;
    private EmploymentType employmentType;
    private Location location;
    private DegreeOfDuty degreeOfDuty;
    private OrderBy orderBy;
    private String city;
    private String address;
    private LocalDate publishedDate;
    private LocalDate deadline;
    private boolean isNew;
    private String contactEmail;
}
