package com.modhtom.linkguardbackend.controller;

import com.modhtom.linkguardbackend.DTO.UserRequestDTO;
import com.modhtom.linkguardbackend.DTO.UserRespondDTO;
import com.modhtom.linkguardbackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication", description = "Endpoints for user registration and login.")
public class AuthController {
    @Autowired
    AuthService service;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and an associated empty portfolio. Returns the created user's details."
    )
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided or username is already taken")
    @PostMapping("/register")
    public ResponseEntity<UserRespondDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        UserRespondDTO user = service.addUser(userRequestDTO);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Verifies a user's credentials and returns a JWT (JSON Web Token) upon success."
    )
    @ApiResponse(responseCode = "200", description = "Authentication successful, JWT is returned")
    @ApiResponse(responseCode = "400", description = "Invalid credentials provided")
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO userRequestDTO) {
        String ret = service.verify(userRequestDTO);
        if (ret == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        System.out.println(ret);
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }
}
