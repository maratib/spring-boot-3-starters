package com.mak.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1")
// @PreAuthorize("hasRole('ADMIN')")
public class Admin {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping()
    public String index() throws Exception {
        return "Admin V1 : " + appVersion;
    }

    @GetMapping("/user")
    public String user() throws Exception {
        return "User V1 : " + appVersion;
    }

}
