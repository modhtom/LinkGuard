package com.modhtom.linkguardbackend.service;

import com.modhtom.linkguardbackend.DTO.UserRequestDTO;
import com.modhtom.linkguardbackend.DTO.UserRespondDTO;
import com.modhtom.linkguardbackend.model.User;
import com.modhtom.linkguardbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @Transactional
    public UserRespondDTO addUser(UserRequestDTO userRequestDTO) {
        if (repo.findUserByUsername(userRequestDTO.getUsername())!=null) {
            throw new IllegalStateException("Username is already taken");
        }

        User newUser = mapToUser(userRequestDTO);

        User savedUser = repo.save(newUser);

        return mapToUserRespondDTO(savedUser);
    }

    private User mapToUser(UserRequestDTO user) {
        User newuser = new User();
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        newuser.setUsername(user.getUsername());
        newuser.setEmail(user.getMail());
        newuser.setPassword_hash(bCryptPasswordEncoder.encode(user.getPassword_unHashed()));
        newuser.setRoles(roles);
        newuser.setEnabled(true);
        newuser.setCreated_at(new Date());
        newuser.setUpdated_at(new Date());
        return newuser;
    }

    private UserRespondDTO mapToUserRespondDTO(User user) {
        UserRespondDTO dto = new UserRespondDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setMail(user.getEmail());
        dto.setEnabled(user.getEnabled());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        return dto;
    }

    public String verify(UserRequestDTO userRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(),userRequestDTO.getPassword_unHashed()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(userRequestDTO.getUsername());
        }
        return null;
    }
}
