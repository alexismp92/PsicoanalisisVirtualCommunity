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
public class CommunitySts {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_sts_seq")
    @SequenceGenerator(name = "community_sts_seq", sequenceName = "community_sts_sequence", allocationSize = 1)
    private Long communityStsId;
    private String commStatusName;
}
