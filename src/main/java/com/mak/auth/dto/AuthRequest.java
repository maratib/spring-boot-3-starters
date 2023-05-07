package com.mak.auth.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Username is required")
    @Length(min = 4, max = 50)
    private String username;

    @NotBlank(message = "Password is required")
    @Length(min = 4, max = 50)
    private String password;
}
