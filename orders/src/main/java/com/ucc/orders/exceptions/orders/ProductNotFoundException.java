package com.ucc.orders.exceptions.orders;

import com.ucc.orders.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
    }
} 