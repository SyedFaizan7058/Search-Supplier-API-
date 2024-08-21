package com.nit.handler;

import com.nit.dto.ExceptionInfo;
import com.nit.exception.SupplierNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionInfo> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Field '" + fieldError.getField() + "' in class '"
                        + fieldError.getObjectName() + "' - " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setTimestamp(LocalDateTime.now());
        exceptionInfo.setStatus(String.valueOf(ex.getStatusCode()));
        exceptionInfo.setMessage(errorMessage);

        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<String> handleSupplierException(Exception ex) {

        String errorMessage = ex.getMessage();
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setTimestamp(LocalDateTime.now());
        exceptionInfo.setStatus(HttpStatus.NOT_FOUND.toString());
        exceptionInfo.setMessage(errorMessage);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> handleInternalException(Exception ex) {

        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setTimestamp(LocalDateTime.now());
        exceptionInfo.setMessage("Server issue, please try after some time !");
        exceptionInfo.setStatus(ex.getClass().getSimpleName());

        return new ResponseEntity<>(exceptionInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionInfo> handleNoResourceException(Exception ex) {

        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setTimestamp(LocalDateTime.now());
        exceptionInfo.setMessage("Resource not found check the url and please try again");
        exceptionInfo.setStatus(HttpStatus.NOT_FOUND.toString());

        return new ResponseEntity<>(exceptionInfo, HttpStatus.NOT_FOUND);
    }

}

