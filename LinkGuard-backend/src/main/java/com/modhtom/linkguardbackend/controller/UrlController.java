package com.modhtom.linkguardbackend.controller;

import com.modhtom.linkguardbackend.DTO.UrlRequestDTO;
import com.modhtom.linkguardbackend.DTO.UrlRespondDTO;
import com.modhtom.linkguardbackend.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;

@RestController
@RequestMapping("/api/urls")
@Tag(name = "URL Management", description = "Endpoints for url Submit a short URL and receive expanded details. Must cache results in Redis.")
@SecurityRequirement(name = "Bearer Authentication")
public class UrlController {
    private final UrlService urlService;
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(
            summary = "Submit a short URL and receive expanded details"
    )
    @ApiResponse(responseCode = "200", description = "Receive expanded details successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data provided")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @PostMapping("/v1/expand")
    public ResponseEntity<UrlRespondDTO> expand(Principal principal,@RequestBody UrlRequestDTO request) throws IOException, URISyntaxException {
        UrlRespondDTO urlDetails = urlService.expand(principal,request);
        return new ResponseEntity<>(urlDetails, HttpStatus.OK);
    }

}
