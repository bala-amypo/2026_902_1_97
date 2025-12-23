package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "certificate_templates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "templateName")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String templateName;

    @Column(nullable = false)
    private String backgroundUrl;

    @Column(nullable = false)
    private String fontStyle;

    @Column(nullable = false)
    private String signatureName;
}