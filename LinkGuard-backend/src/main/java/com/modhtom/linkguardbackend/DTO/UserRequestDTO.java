package com.modhtom.linkguardbackend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @Schema(description = "Username for the account.", example = "modhtom", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @Schema(description = "User's email address.", example = "modhtom@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Email
    private String mail;

    @Schema(description = "User's password (plain text).", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Size(min = 6)
    private String password_unHashed;
}
