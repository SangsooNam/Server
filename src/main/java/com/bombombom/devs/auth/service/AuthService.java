package com.bombombom.devs.auth.service;

import com.bombombom.devs.auth.service.dto.AuthenticationResult;
import com.bombombom.devs.global.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationResult authenticate(UsernamePasswordAuthenticationToken token) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);

//            322
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e.getCause());
        }
        return AuthenticationResult.builder()
            .accessToken(jwtUtils.generateAccessToken(authentication))
            .refreshToken(jwtUtils.generateRefreshToken(authentication))
            .build();
    }
}
