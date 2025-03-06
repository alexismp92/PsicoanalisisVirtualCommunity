package com.psicovirtual.community.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest_seq")
    @SequenceGenerator(name = "interest_seq", sequenceName = "interest_sequence", allocationSize = 1)
    private Long interestId;
    private boolean isCourse;
    private boolean isStudyGroups;
    private boolean isSupervisions;
    private boolean isSocialMediaPromotions;
    @OneToOne(mappedBy = "interests")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Therapist therapist;
}
