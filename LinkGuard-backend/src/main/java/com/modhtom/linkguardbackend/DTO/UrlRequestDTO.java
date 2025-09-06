package com.modhtom.linkguardbackend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlRequestDTO {
    @Schema(description = "The url to get expand.", example = "https://tinyurl.com/34775fb4", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    String url;
}
