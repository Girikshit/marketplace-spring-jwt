package com.marketplace.demo.auth;

import com.marketplace.demo.models.Role;
import com.marketplace.demo.models.User;
import com.marketplace.demo.repositories.UserRepository;
import com.marketplace.demo.services.JwtService;
import com.marketplace.demo.services.UserService;
import lombok.AllArgsConstructor;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
        try {
            var user= User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userService.addUser(user);
            var jwtToken = jwtService.generateTokenWithRoles(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error while registering:" ,e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user= userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateTokenWithRoles(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
