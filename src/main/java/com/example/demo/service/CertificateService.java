package com.example.demo.service;

import com.example.demo.entity.Certificate;
import com.example.demo.entity.Student;
import com.example.demo.entity.CertificateTemplate;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final StudentService studentService;
    private final TemplateService templateService;

    public CertificateService(CertificateRepository certificateRepository,
                              StudentService studentService,
                              TemplateService templateService) {
        this.certificateRepository = certificateRepository;
        this.studentService = studentService;
        this.templateService = templateService;
    }

    @Transactional
    public Certificate generateCertificate(Long studentId, Long templateId) {

        Student student = studentService.findById(studentId);

        CertificateTemplate template = templateService.findById(templateId);
        if (template == null) {
            throw new ResourceNotFoundException(
                    "Certificate template not found with id: " + templateId);
        }

        String verificationCode = UUID.randomUUID().toString();
        String qrCodeUrl =
                "https://api.qrserver.com/v1/create-qr-code/?data=" + verificationCode;

        Certificate certificate = Certificate.builder()
                .student(student)
                .template(template)
                .issuedDate(LocalDate.now())
                .verificationCode(verificationCode)
                .qrCodeUrl(qrCodeUrl)
                .build();

        return certificateRepository.save(certificate);
    }

    public Certificate getCertificate(Long certificateId) {
        return certificateRepository.findById(certificateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Certificate not found with id: " + certificateId));
    }

    public Certificate findByVerificationCode(String code) {
        return certificateRepository.findByVerificationCode(code).orElse(null);
    }

    public List<Certificate> findByStudentId(Long studentId) {
        Student student = studentService.findById(studentId);
        return certificateRepository.findByStudent(student);
    }
}