package com.example.demo.controller;

import com.example.demo.entity.VerificationLog;
import com.example.demo.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/verify")
@Tag(name = "Verification")
public class VerificationController {
    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
    @PostMapping("/{verificationCode}")
    @Operation(summary = "Verify certificate")
    public ResponseEntity<VerificationLog> verify(@PathVariable String verificationCode, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        return ResponseEntity.ok(verificationService.verifyCertificate(verificationCode, clientIp));
    }
    @GetMapping("/logs/{certificateId}")
    @Operation(summary = "Get verification logs")
    public ResponseEntity<List<VerificationLog>> getLogs(@PathVariable Long certificateId) {
        return ResponseEntity.ok(verificationService.getLogsByCertificate(certificateId));
    }
}