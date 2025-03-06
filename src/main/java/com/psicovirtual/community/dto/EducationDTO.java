package com.psicovirtual.community.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {

    private Long educationId;
    @NotBlank
    @Size(min = 2, max = 100, message = "institution name should be between 2 - 100 characters")
    private String institution;
    @Positive
    private int graduationYear;
    @NotBlank
    private String certificateFilename;
    private String certificatePath;
    @NotBlank
    @Size(min = 2, max = 2, message = "country must be iso 2 format")
    private String country;
}
