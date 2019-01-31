package com.github.spring.grpc.view.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class JwtUserModel {
    private long id;
    private String username;
    private String role;
    private String token;

    public JwtUserModel(long id, String username, String token, String role) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.role = role;
    }
}
