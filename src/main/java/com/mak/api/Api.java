package com.mak.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.mak.auth.dto.AuthRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
// @PreAuthorize("hasRole('USER')")
public class Api {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping()
    public String index() throws Exception {
        // return "API V1 : " + appVersion;
        throw new Exception("Some Exception");

    }

    @GetMapping("/path-variable/{first}/{second}")
    public String pathVariable(@PathVariable String first, @PathVariable String second) {

        return String.format("API : First:%s Second:%s ", first, second);
    }

    @GetMapping("/request-param")
    public String requestParam(@RequestParam String first,
            @RequestParam(required = false, defaultValue = "abc") String second) {

        return String.format("API : First:%s Second:%s ", first, second);
    }

    @PostMapping("/post-validate")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
        // System.out.println("Auth : " + authRequest);

        return "Hello Post : " + authRequest;
    }

}
