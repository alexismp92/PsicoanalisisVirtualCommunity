package com.psicovirtual.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestDTO {

    private Long interestId;
    private Boolean isCourse;
    private Boolean isStudyGroups;
    private Boolean isSupervisions;
    private Boolean isSocialMediaPromotions;
}
