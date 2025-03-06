package com.psicovirtual.community.dto;

import io.swagger.v3.oas.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @Parameter(description = "Certificate file in JPG, PNG or PDF format")
    private Set<MultipartFile> files;
    @Parameter(description = "Therapist information required to join to the community. Its required to send it in json format")
    private String therapistDTO;
}
