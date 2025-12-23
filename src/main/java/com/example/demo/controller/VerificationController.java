package com.example.demo.controller;

import com.example.demo.entity.VerificationLog;
import com.example.demo.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/verify")
@Tag(name = "Verification", description = "Certificate verification endpoints")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/{verificationCode}")
    @Operation(summary = "Verify certificate")
    public ResponseEntity<VerificationLog> verifyCertificate(@PathVariable String verificationCode,
                                                             HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        VerificationLog log = verificationService.verifyCertificate(verificationCode, clientIp);

        if (log.getStatus().equals("FAILED")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(log);
        }

        return ResponseEntity.ok(log);
    }

    @GetMapping("/logs/{certificateId}")
    @Operation(summary = "Get verification logs by certificate ID")
    public ResponseEntity<List<VerificationLog>> getVerificationLogs(@PathVariable Long certificateId) {
        List<VerificationLog> logs = verificationService.getLogsByCertificate(certificateId);

        if (logs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(logs);
    }
}