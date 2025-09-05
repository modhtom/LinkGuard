package com.modhtom.linkguardbackend.DTO;

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
    private UUID id;
    private String shortUrl;
    private String finalUrl;
    private String domain;
    private String title;
    private String description;
}
