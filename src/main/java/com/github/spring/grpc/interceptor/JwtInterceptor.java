package com.github.spring.grpc.interceptor;

import com.github.spring.grpc.config.security.UsernameAuthenticationToken;
import io.grpc.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.google.common.base.Strings.nullToEmpty;

@Log4j2
public class JwtInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String authHeader = nullToEmpty(headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)));
        log.info(authHeader);
        if (!authHeader.startsWith("Bearer ")) {
            SecurityContextHolder.clearContext();
            return next.startCall(call, headers);
        }
        try {
            String token = authHeader.substring(7);
            log.info("Bearer Token Authorization header found");
            UsernameAuthenticationToken auth = new UsernameAuthenticationToken(token);
            log.info("Authentication success: {}", auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            log.info("Authentication request failed: {}", e.getMessage());
            throw Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e).asRuntimeException();
        }

        return next.startCall(call, headers);
    }
}
