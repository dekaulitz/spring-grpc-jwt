package com.github.spring.grpc.exception.handler;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.github.spring.grpc.exception.InternalAppsException;
import com.github.spring.grpc.view.model.ErrorModel;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InternalAppsException.class)
    public ResponseEntity<Object> allException(Exception ex, WebRequest request) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                ex.getLocalizedMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
