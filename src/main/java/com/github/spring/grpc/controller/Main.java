package com.github.spring.grpc.controller;

import com.github.spring.grpc.config.security.model.UserDetailModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/berak")
public class Main {
    @RequestMapping(method = RequestMethod.GET, value = "")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity index() {
        UserDetailModel userDetailModel = (UserDetailModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body("string" + userDetailModel.getUsername());
    }
}
