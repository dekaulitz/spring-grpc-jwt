package com.github.spring.grpc.exception.handler;


import com.github.spring.grpc.exception.InternalAppsException;
import com.github.spring.grpc.view.model.ErrorModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
public class PageErorrHandlerController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity handleError(HttpServletRequest request) {
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (exception instanceof InternalAppsException) {
            ErrorModel errorModel = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, ((InternalAppsException) exception).getMessage(), ((InternalAppsException) exception).getLocalizedMessage());
            return new ResponseEntity<>(errorModel, errorModel.getStatus());
        }
        return null;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
