package com.psicovirtual.community.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "IDX_THERAPIST_01", columnList = "email")
})
public class Therapist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "therapist_seq")
    @SequenceGenerator(name = "therapist_seq", sequenceName = "therapist_sequence", allocationSize = 1)
    private Long therapistId;
    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    private String secLastName;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(unique = true, nullable = false)
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
    @CreationTimestamp
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy = "therapist", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Education> educations;
    private int yearsOfExperience;
    private String experienceDesc;
    private String motivationDesc;
    private int rate;
    @Column(name = "is_migration_exp")
    private boolean isMigrationExperience;
    @Column(name = "is_adjst_rate")
    private boolean isOpenToAdjustRate;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "interest_id", referencedColumnName = "interestId")
    private Interest interests;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "community_req_id", referencedColumnName = "communityReqId")
    private CommunityReq communityRequest;

}
