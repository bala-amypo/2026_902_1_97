package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "certificates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "verificationCode")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private CertificateTemplate template;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @Column(nullable = false)
    private String qrCodeUrl;

    @Column(nullable = false, unique = true)
    private String verificationCode;
}