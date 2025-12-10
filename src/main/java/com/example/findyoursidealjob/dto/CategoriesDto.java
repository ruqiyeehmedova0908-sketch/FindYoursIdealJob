package com.example.findyoursidealjob.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {
    private Long id;
    private String name;
    private String icon;
}
