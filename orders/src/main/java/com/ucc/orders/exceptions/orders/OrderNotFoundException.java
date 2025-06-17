package com.ucc.orders.exceptions.orders;

import com.ucc.orders.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND");
    }
} 