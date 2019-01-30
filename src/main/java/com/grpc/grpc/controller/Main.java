package com.grpc.grpc.controller;

import com.grpc.grpc.config.security.model.UserDetailModel;
import com.grpc.grpc.exception.InternalAppsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/berak")
public class Main {
    private UserDetailModel userDetailModel;

    @RequestMapping(method = RequestMethod.GET, value = "")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity index() {
        throw new InternalAppsException("berak");
//        this.userDetailModel = (UserDetailModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return ResponseEntity.status(HttpStatus.OK).body("string" + userDetailModel.getUsername());
    }
}
