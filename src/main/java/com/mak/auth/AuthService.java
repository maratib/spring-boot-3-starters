package com.mak.auth;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mak.auth.dto.AuthRequest;
import com.mak.auth.dto.AuthResponse;
import com.mak.auth.model.Role;
import com.mak.auth.model.Token;
import com.mak.auth.model.TokenType;
import com.mak.auth.model.TokenRepo;
import com.mak.auth.model.User;
import com.mak.auth.model.UserRepo;
import com.mak.exceptions.DBException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        // Optional<User> user = userRepo.findByEmail(username);
        // return user.map(UserInfoDetails::new)
        // .orElseThrow(() -> new UsernameNotFoundException("user not found " +
        // username));

        return findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found " +
                username));

    }

    private User save(User user) throws DBException {
        var userAlreadyExists = userRepo.findByEmail(user.getEmail());
        if (userAlreadyExists.isPresent()) {
            throw new DBException(String.format("[%s] %s", user.getEmail(), "User Already Exists"));
        }
        User newUser = userRepo.save(user);

        return newUser;
    }

    public User createUser(String email, String password, Role role) throws DBException {
        User user = new User();
        // user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(role.toString());
        save(user);
        return user;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public AuthResponse doAuthentication(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        // var userDetails = loadUserByUsername(request.getUsername());
        var user = findByEmail(request.getUsername()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        System.out.println("Going through here logout : " + refreshToken + userEmail);
        if (userEmail != null) {
            var user = findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
