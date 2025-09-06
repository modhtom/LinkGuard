package com.modhtom.linkguardbackend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlRespondDTO {
    @Schema(description = "An ID for the url that got expand.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID id;
    @Schema(description = "The URL that needs to be expand.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String shortUrl;
    @Schema(description = "The URL that got expand.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String finalUrl;
    @Schema(description = " Domain Name for the url that got expand.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String domain;
    @Schema(description = "Domain Title for the url that got expand.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String title;
    @Schema(description = "Domain Description for the url that got expand.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String description;
}
