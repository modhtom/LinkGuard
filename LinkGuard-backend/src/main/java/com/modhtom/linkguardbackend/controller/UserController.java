package com.modhtom.linkguardbackend.controller;

import com.modhtom.linkguardbackend.DTO.UserRequestDTO;
import com.modhtom.linkguardbackend.DTO.UserRespondDTO;
import com.modhtom.linkguardbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Profile Management", description = "Endpoints for users to manage their own profiles.")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    UserService service;

    @Operation(
            summary = "Update a user's profile",
            description = "Allows an authenticated user to update their own profile information (e.g., email, password)."
    )
    @ApiResponse(responseCode = "200", description = "Profile updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data provided")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @PutMapping("/edit")
    public ResponseEntity<UserRespondDTO> updateUser(Principal principal, @RequestBody UserRequestDTO userRequestDTO) {
        UserRespondDTO updatedUser = service.updateUser(principal.getName(), userRequestDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Get current user's details",
            description = "Fetches the profile information for the currently authenticated user based on their JWT."
    )
    @ApiResponse(responseCode = "200", description = "User details retrieved successfully")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @GetMapping("/me")
    public ResponseEntity<UserRespondDTO> getUser(Principal principal) {
        UserRespondDTO user = service.getUser(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
