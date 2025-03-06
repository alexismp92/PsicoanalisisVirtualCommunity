package com.psicovirtual.community.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "IDX_COMMUNITY_REQ_01", columnList = "community_sts_id")
})
public class CommunityReq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_req_seq")
    @SequenceGenerator(name = "community_req_seq", sequenceName = "community_req_sequence", allocationSize = 1)
    private Long communityReqId;
    @ManyToOne
    @JoinColumn(name = "community_sts_id")
    private CommunitySts communityStatus;
    @CreationTimestamp
    private LocalDateTime reviewDate;
    private String rejectedReason;
    @OneToOne(mappedBy = "communityRequest")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Therapist therapist;
}
