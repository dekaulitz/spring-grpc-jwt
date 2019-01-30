package com.grpc.grpc.config.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UsernameAuthenticationToken extends UsernamePasswordAuthenticationToken {
    @Getter
    private String token;

    public UsernameAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}