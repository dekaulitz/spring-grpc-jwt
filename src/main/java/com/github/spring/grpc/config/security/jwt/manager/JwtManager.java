package com.github.spring.grpc.config.security.jwt.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.spring.grpc.view.model.JwtUserModel;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

@Log4j2
public class JwtManager {
    public static final String SECCRET = "qwERTyuIODfghjK@#$%^7888ghjkbnhjkxzxzxAJSDHJASDHJHJS";

    public static String generateToken(JwtUserModel jwtUserModel) {
        Algorithm algorithm = Algorithm.HMAC256(SECCRET);
        return JWT.create()
                .withClaim("id", jwtUserModel.getId())
                .withClaim("username", jwtUserModel.getUsername())
                .withClaim("roles", jwtUserModel.getRole())
                .withIssuedAt(new Date()).sign(algorithm);
    }

    public static JwtUserModel validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECCRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        JwtUserModel jwtUserModel = new JwtUserModel();
        jwtUserModel.setId(jwt.getClaim("id").asLong());
        jwtUserModel.setUsername(jwt.getClaim("username").asString());
        jwtUserModel.setRole(jwt.getClaim("roles").asString());
        return jwtUserModel;
    }
}
