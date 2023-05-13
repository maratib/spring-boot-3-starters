package com.mak.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mak.auth.dto.AuthRequest;
import com.mak.auth.dto.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class Auth {

    // @Autowired
    // private JwtService jwtService;

    @Value("${app.version}")
    private String version;

    @Lazy
    @Autowired
    private AuthService service;

    @GetMapping("/")
    String home() {
        return String.format("API : %s ", version);
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request) {
        System.out.println("Here we are " + request);
        return ResponseEntity.ok(service.doAuthentication(request));
    }

    @PostMapping("/auth/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
