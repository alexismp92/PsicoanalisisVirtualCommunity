package com.psicovirtual.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityReqDTO {
    @Positive
    private Long communityReqId;
    @NotBlank
    private String communityStatus;
    @DateTimeFormat(pattern = "DD-MM-YYYY HH:mm:ss")
    private LocalDateTime reviewDate;
    private String rejectedReason;
}
