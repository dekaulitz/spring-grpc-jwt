package com.github.spring.grpc.controller;

import com.github.spring.grpc.config.security.jwt.manager.JwtManager;
import com.github.spring.grpc.config.security.model.UserDetailModel;
import com.github.spring.grpc.entity.UserEntity;
import com.github.spring.grpc.exception.InternalAppsException;
import com.github.spring.grpc.repository.UserRepository;
import com.github.spring.grpc.view.model.JwtUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class Main {
    @Autowired
    private UserRepository userRepository;

    public Main(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/api/greeting")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity index() {
        UserDetailModel userDetailModel = (UserDetailModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = this.userRepository.getOne(userDetailModel.getId());
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtUserModel> login(@RequestBody UserEntity body) {
        UserEntity userEntity = this.userRepository.findByEmail(body.getEmail());
        if (userEntity != null) {
            JwtUserModel jwtUserModel = new JwtUserModel();
            jwtUserModel.setId(userEntity.getId());
            jwtUserModel.setRole(userEntity.getRole());
            jwtUserModel.setUsername(userEntity.getRole());
            String token = JwtManager.generateToken(jwtUserModel);
            jwtUserModel.setToken(token);
            return ResponseEntity.ok(jwtUserModel);
        } else throw new InternalAppsException("user not found");
    }


}
