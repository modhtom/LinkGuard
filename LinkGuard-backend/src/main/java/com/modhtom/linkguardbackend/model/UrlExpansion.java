package com.modhtom.linkguardbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "UrlExpansion")
public class UrlExpansion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String shortUrl;
    private String finalUrl;
    private String domain;
    private String title;
    private String description;
    @CreationTimestamp
    private Date resolvedAt;
    @OneToOne
    private User resolvedBy;
}