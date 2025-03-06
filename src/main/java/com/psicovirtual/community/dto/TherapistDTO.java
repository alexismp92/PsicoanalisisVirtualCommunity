package com.psicovirtual.community.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapistDTO {
    private Long therapistId;
    @NotBlank
    @Size(min = 2, max = 50, message = "firstname should be between 2 - 50 characters")
    private String firstName;
    private String middleName;
    @NotBlank
    @Size(min = 2, max = 50, message = "lastname should be between 2 - 50 characters")
    private String lastName;
    private String secLastName;
    @DateTimeFormat(pattern = "DD-MM-YYYY")
    private LocalDate dateOfBirth;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Email
    private String confirmEmail;
    @NotBlank
    @Size(min = 2, max = 10, message = "gender should be between 2 - 10 characters")
    private String gender;
    @NotBlank
    @Size(min = 2, max = 2, message = "country must be iso 2 format")
    private String country;
    @Valid
    private Set<EducationDTO> educations;
    @PositiveOrZero
    @Max(value = 100, message = "years of experience should be less than 100")
    private Integer yearsOfExperience;
    @NotBlank
    @Size(max = 500, message = "experience description must be less than 500 character")
    private String experienceDesc;
    @NotBlank
    @Size(max = 500, message = "experience description must be less than 500 character")
    private String motivationDesc;
    @Positive
    private Integer rate;
    private Boolean isMigrationExperience;
    private Boolean isOpenToAdjustRate;
    private InterestDTO interests;
}
