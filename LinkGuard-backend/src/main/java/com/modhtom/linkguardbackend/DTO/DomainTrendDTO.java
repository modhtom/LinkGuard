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
public class DomainTrendDTO {
    @Schema(description = "Trending Domain Name.", example = "modhtom.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String domain;
    @Schema(description = "Trending Domain Count.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Double count;
}
