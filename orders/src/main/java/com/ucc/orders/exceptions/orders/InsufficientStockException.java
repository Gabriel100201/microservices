package com.ucc.orders.exceptions.orders;

import com.ucc.orders.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientStockException extends BaseException {
    public InsufficientStockException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INSUFFICIENT_STOCK");
    }
} 