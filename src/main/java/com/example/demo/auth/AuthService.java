package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.model.auth.Role;
import com.example.demo.model.auth.User;
import com.example.demo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthRegisterRequest request) {
        request.setRole(Role.ADMINISTRATOR);
        var user = saveUser(request, null);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public User saveUser(AuthRegisterRequest request, Role role) {
        var user = User.builder()
                .name(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isEnabled(true)
                .build();
        userRepo.save(user);
        return user;
    }

    public User saveUser(AuthRegisterRequest request) {
        var user = User.builder()
                .name(request.getFirstname().concat(" ").concat(request.getLastname()))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isEnabled(true)
                .build();
        User savedUser = userRepo.save(user);
        return user;
    }

    public AuthenticationResponse authenticate(AuthLoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
