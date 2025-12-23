package com.example.demo.service;

import com.example.demo.entity.Certificate;
import com.example.demo.entity.VerificationLog;
import com.example.demo.repository.VerificationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerificationService {

    private final VerificationLogRepository verificationLogRepository;
    private final CertificateService certificateService;

    public VerificationService(VerificationLogRepository verificationLogRepository,
                               CertificateService certificateService) {
        this.verificationLogRepository = verificationLogRepository;
        this.certificateService = certificateService;
    }

    @Transactional
    public VerificationLog verifyCertificate(String verificationCode, String clientIp) {

        Certificate certificate =
                certificateService.findByVerificationCode(verificationCode);

        String status = (certificate != null) ? "SUCCESS" : "FAILED";

        VerificationLog verificationLog = VerificationLog.builder()
                .certificate(certificate) // can be null if FAILED
                .verifiedAt(LocalDateTime.now())
                .status(status)
                .ipAddress(clientIp)
                .build();

        return verificationLogRepository.save(verificationLog);
    }

    public List<VerificationLog> getLogsByCertificate(Long certificateId) {
        return verificationLogRepository.findByCertificateId(certificateId);
    }
}