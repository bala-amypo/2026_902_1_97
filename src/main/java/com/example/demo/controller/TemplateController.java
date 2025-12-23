package com.example.demo.controller;

import com.example.demo.entity.CertificateTemplate;
import com.example.demo.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/templates")
@Tag(name = "Templates", description = "Certificate template management endpoints")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    @Operation(summary = "Add new template")
    public ResponseEntity<CertificateTemplate> addTemplate(@Valid @RequestBody CertificateTemplate template) {
        CertificateTemplate savedTemplate = templateService.addTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
    }

    @GetMapping
    @Operation(summary = "Get all templates")
    public ResponseEntity<List<CertificateTemplate>> getAllTemplates() {
        List<CertificateTemplate> templates = templateService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{templateId}")
    @Operation(summary = "Get template by ID")
    public ResponseEntity<CertificateTemplate> getTemplateById(@PathVariable Long templateId) {
        CertificateTemplate template = templateService.findById(templateId);
        if (template != null) {
            return ResponseEntity.ok(template);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}