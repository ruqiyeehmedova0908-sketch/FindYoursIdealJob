package com.example.findyoursidealjob.dto;
import com.example.findyoursidealjob.enums.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacanciesDetailDto {
    private Long id;                    // Vakansiya Detail ID
    private Long vacanciesId;           // Vakansiya ID
    private String title;               // Elanın adı
    private boolean isNew;              // Yeni elan olub-olmaması
    private LocalDate publishedDate;    // Yayım tarixi
    private LocalDate deadline;         // Son müraciət tarixi
    private String requirements;        // Tələblər
    private String responsibilities;
    private String description;         // Təsvir
    private String benefits;
    // Faydalı şərtlər
    private int monthlyViews;           // Aylıq baxış
    private String contactEmail;        // Müraciət emaili

}
