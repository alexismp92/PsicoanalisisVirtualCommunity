package com.psicovirtual.community.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_seq")
    @SequenceGenerator(name = "education_seq", sequenceName = "education_sequence", allocationSize = 1)
    private Long educationId;
    @Column(nullable = false)
    private String institution;
    @Column(nullable = false)
    private int graduationYear;
    @Column(nullable = false)
    private String certificatePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Therapist therapist;
}
