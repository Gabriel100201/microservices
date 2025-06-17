package com.ucc.orders.exceptions.orders;

import com.ucc.orders.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class OrderValidationException extends BaseException {
    public OrderValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "ORDER_VALIDATION_ERROR");
    }
} 