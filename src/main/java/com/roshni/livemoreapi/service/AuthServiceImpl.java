package com.roshni.livemoreapi.service;

import com.roshni.livemoreapi.dto.request.LoginRequest;
import com.roshni.livemoreapi.dto.request.RefreshTokenRequest;
import com.roshni.livemoreapi.dto.request.RegisterRequest;
import com.roshni.livemoreapi.dto.response.AuthResponse;
import com.roshni.livemoreapi.dto.response.UserResponse;
import com.roshni.livemoreapi.entity.User;
import com.roshni.livemoreapi.enums.Role;
import com.roshni.livemoreapi.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        if(userService.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already registered: " + registerRequest.getEmail());
        }

        // Create new user with hashed password
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .role(Role.USER)
                .emailVerified(false)
                .isDeleted(false)
                .build();

        // Save user
        user = userService.createUser(user);

        // Generate tokens and return response
        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Find user by email
        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate tokens and return response
        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        //validate
        if(!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        // type is refresh not access
        Claims claims = jwtUtil.extractAllClaims(refreshToken);
        String tokenType = claims.get("type", String.class);
        if(!"refresh".equals(tokenType)) {
            throw  new RuntimeException("Invalid token type");
        }

        // Get user from token
        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken  = jwtUtil.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiration / 1000) // Convert ms to seconds
                .user(UserResponse.fromEntity(user))
                .build();
    }
}
