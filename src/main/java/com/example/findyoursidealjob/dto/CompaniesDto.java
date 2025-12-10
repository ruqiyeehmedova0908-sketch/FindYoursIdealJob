package com.example.findyoursidealjob.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompaniesDto {
    private Long id;
    private String name;
    private String icon;
    private Long vacancyCount;
}
