package com.roshni.livemoreapi.service;

import com.roshni.livemoreapi.dto.request.LoginRequest;
import com.roshni.livemoreapi.dto.request.RefreshTokenRequest;
import com.roshni.livemoreapi.dto.request.RegisterRequest;
import com.roshni.livemoreapi.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
