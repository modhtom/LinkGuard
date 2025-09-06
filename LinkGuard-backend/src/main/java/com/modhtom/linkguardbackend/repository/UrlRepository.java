package com.modhtom.linkguardbackend.repository;

import com.modhtom.linkguardbackend.model.UrlExpansion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UrlRepository extends JpaRepository<UrlExpansion, UUID> {
    @Query("SELECT ue FROM UrlExpansion ue WHERE ue.domain IN :domains AND ue.resolvedAt = " +
            "(SELECT MAX(ue2.resolvedAt) FROM UrlExpansion ue2 WHERE ue2.domain = ue.domain)")
    List<UrlExpansion> findLatestExpansionForDomains(@Param("domains") List<String> domains);
}
