package com.psicovirtual.community.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "IDX_GENDER_01", columnList = "gender_name")
})
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gender_seq")
    @SequenceGenerator(name = "gender_seq", sequenceName = "gender_sequence", allocationSize = 1)
    private Long genderId;
    @Column(nullable = false, unique = true)
    private String genderName;
}
