package com.psicovirtual.community.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "IDX_EMAIL_01", columnList = "type"),
})
public class EmailConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_config_seq")
    @SequenceGenerator(name = "email_config_seq", sequenceName = "email_config_sequence", allocationSize = 1)
    private Long emailId;
    @Column(nullable = false, unique = true)
    private String type;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String message;
}