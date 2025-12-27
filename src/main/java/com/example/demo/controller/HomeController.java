package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Tag(name = "Home")
public class HomeController {
    @GetMapping("/")
    @Operation(summary = "Welcome message")
    public String home() {
        return "Welcome to Digital Certificate Generator API! Visit /swagger-ui.html for API documentation.";
    }
}