package com.example.demo.controller;

import com.example.demo.entity.Certificate;
import com.example.demo.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@Tag(name = "Certificates", description = "Certificate management endpoints")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/generate/{studentId}/{templateId}")
    @Operation(summary = "Generate certificate")
    public ResponseEntity<Certificate> generateCertificate(@PathVariable Long studentId,
                                                           @PathVariable Long templateId) {
        Certificate certificate = certificateService.generateCertificate(studentId, templateId);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificate);
    }

    @GetMapping("/{certificateId}")
    @Operation(summary = "Get certificate details")
    public ResponseEntity<Certificate> getCertificate(@PathVariable Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        return ResponseEntity.ok(certificate);
    }

    @GetMapping("/verify/code/{verificationCode}")
    @Operation(summary = "Fetch certificate by verification code")
    public ResponseEntity<Certificate> getCertificateByCode(@PathVariable String verificationCode) {
        Certificate certificate = certificateService.findByVerificationCode(verificationCode);
        if (certificate != null) {
            return ResponseEntity.ok(certificate);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get certificates by student")
    public ResponseEntity<List<Certificate>> getCertificatesByStudent(@PathVariable Long studentId) {
        List<Certificate> certificates = certificateService.findByStudentId(studentId);
        return ResponseEntity.ok(certificates);
    }
}