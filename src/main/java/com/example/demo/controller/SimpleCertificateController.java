package com.example.demo.controller;

import com.example.demo.entity.Certificate;
import com.example.demo.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class SimpleCertificateController {
    
    private final CertificateService certificateService;

    public SimpleCertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/generate/{studentId}/{templateId}")
    public ResponseEntity<Certificate> generateCertificate(@PathVariable Long studentId, 
                                                          @PathVariable Long templateId) {
        Certificate certificate = certificateService.generateCertificate(studentId, templateId);
        return ResponseEntity.ok(certificate);
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        return ResponseEntity.ok(certificate);
    }

    @GetMapping("/verify/{verificationCode}")
    public ResponseEntity<Certificate> getCertificateByCode(@PathVariable String verificationCode) {
        Certificate certificate = certificateService.findByVerificationCode(verificationCode);
        if (certificate != null) {
            return ResponseEntity.ok(certificate);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Certificate>> getCertificatesByStudent(@PathVariable Long studentId) {
        List<Certificate> certificates = certificateService.findByStudentId(studentId);
        return ResponseEntity.ok(certificates);
    }
}