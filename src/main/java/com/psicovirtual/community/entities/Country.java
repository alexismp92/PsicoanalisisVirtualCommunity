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
        @Index(name = "IDX_COUNTRY_01", columnList = "iso3"),
        @Index(name = "IDX_COUNTRY_02", columnList = "phone_code")
})
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
    @SequenceGenerator(name = "country_seq", sequenceName = "country_sequence", allocationSize = 1)
    private Long countryId;
    @Column(nullable = false, unique = true)
    private String nameUp;
    @Column(nullable = false, unique = true)
    private String nameLow;
    @Column(nullable = false, unique = true)
    private String iso2;
    private String iso3;
    private int numCode;
    @Column(nullable = false)
    private String phoneCode;
}
