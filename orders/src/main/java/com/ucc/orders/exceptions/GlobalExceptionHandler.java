package com.ucc.orders.exceptions;

import com.ucc.orders.exceptions.orders.OrderNotFoundException;
import com.ucc.orders.exceptions.orders.OrderValidationException;
import com.ucc.orders.exceptions.orders.InsufficientStockException;
import com.ucc.orders.exceptions.orders.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(
            OrderNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<ErrorResponse> handleOrderValidationException(
            OrderValidationException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(
            InsufficientStockException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(
            ProductNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            "INTERNAL_SERVER_ERROR",
            "Ha ocurrido un error inesperado",
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 