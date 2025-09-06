package com.modhtom.linkguardbackend.controller;

import com.modhtom.linkguardbackend.DTO.DomainTrendDTO;
import com.modhtom.linkguardbackend.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/analytics")
@Tag(name = "Analytics", description = "Endpoints for viewing URL analytics.")
public class AnalyticsController {

    private final UrlService urlService;

    public AnalyticsController(UrlService urlService) {
        this.urlService = urlService;
    }


    @Operation(
            summary = "Fetch the top 10 expanded details from the Submitted short URLs sorted set"
    )
    @ApiResponse(responseCode = "200", description = "Receive the top 10 successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data provided")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @GetMapping("trending")
    public ResponseEntity<List<DomainTrendDTO>> trending(){
        List<DomainTrendDTO> urlList = urlService.getTopTenDomains();
        return new ResponseEntity<>(urlList,HttpStatus.OK);
    }
}