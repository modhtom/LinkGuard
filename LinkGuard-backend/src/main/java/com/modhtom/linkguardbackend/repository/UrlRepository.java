package com.modhtom.linkguardbackend.repository;

import com.modhtom.linkguardbackend.model.UrlExpansion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UrlRepository extends JpaRepository<UrlExpansion, UUID> {
}
