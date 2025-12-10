package com.example.findyoursidealjob.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private Long id;
    private Long vacanciesId;
    private String applicantName;
    private String applicantEmail;
    private String coverLetter;
    private String resumeUrl;
    private LocalDateTime appliedDate;
    private String status;
    private LocalDateTime reviewedDate;
    private String reviewNotes;
}
