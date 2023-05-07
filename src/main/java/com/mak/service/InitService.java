package com.mak.service;

import org.springframework.stereotype.Service;

import com.mak.auth.AuthService;
import com.mak.auth.model.Role;
import com.mak.exceptions.DBException;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class InitService {

    private AuthService authService;

    @PostConstruct
    private void init() {

        log.info("Checking if the admin user exists, if not create one");

        try {
            authService.createUser("admin@gmail.com", "admin", Role.ROLE_ADMIN);
            authService.createUser("user1@gmail.com", "user1", Role.ROLE_USER);
        } catch (DBException e) {
            log.warn(e.getLocalizedMessage());
        }

    }

}
