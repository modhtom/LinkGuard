package com.modhtom.linkguardbackend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespondDTO {
    @Schema(description = "Unique identifier for the user.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID id;

    @Schema(description = "Username of the user.", example = "modhtom")
    private String username;

    @Schema(description = "Email address of the user.", example = "modhtom@example.com")
    private String mail;

    @Schema(description = "Indicates if the user's account is active.", example = "true")
    private Boolean enabled;

    @Schema(description = "Timestamp of when the user was created.")
    private Date created_at;

    @Schema(description = "Timestamp of the last update to the user's profile.")
    private Date updated_at;
}