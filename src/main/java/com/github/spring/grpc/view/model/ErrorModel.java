package com.github.spring.grpc.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {
    private long timestamp = new Date().getTime();
    private HttpStatus status;
    private String message;
    private String error;


    public ErrorModel(HttpStatus httpStatus, String message, String error) {
        this.status = httpStatus;
        this.message = message;
        this.error = error;
    }
}
