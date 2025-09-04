package com.modhtom.linkguardbackend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    @Schema(description = "Timestamp of when the error occurred.")
    private LocalDateTime timestamp;
    @Schema(description = "The HTTP status code.", example = "404")
    private int status;
    @Schema(description = "The HTTP error reason phrase.", example = "Not Found")
    private String error;
    @Schema(description = "A detailed message describing the error.", example = "User with id a1b2c3d4... not found.")
    private String message;
    @Schema(description = "The path of the API endpoint that was called.", example = "uri=/api/users/a1b2c3d4...")
    private String path;
}
