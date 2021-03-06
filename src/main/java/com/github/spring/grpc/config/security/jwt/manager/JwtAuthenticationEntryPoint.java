package com.github.spring.grpc.config.security.jwt.manager;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
/**
 * this is an entrypoint that requested jwt
 * exception handling will excute in here
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    /**
     * request entry point for exception handling
     * ini bakal di periksa pertama kali
     */
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "request not authorized");
    }
}
