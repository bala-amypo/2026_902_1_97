package com.example.demo.service;

import com.example.demo.entity.CertificateTemplate;
import com.example.demo.repository.CertificateTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemplateService {

    private final CertificateTemplateRepository templateRepository;

    public TemplateService(CertificateTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional
    public CertificateTemplate addTemplate(CertificateTemplate template) {

        boolean exists =
                templateRepository.findByTemplateName(template.getTemplateName()).isPresent();

        if (exists) {
            throw new RuntimeException(
                    "Certificate template with this name already exists");
        }

        return templateRepository.save(template);
    }

    public List<CertificateTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    public CertificateTemplate findById(Long id) {
        return templateRepository.findById(id).orElse(null);
    }
}