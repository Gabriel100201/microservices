package com.ucc.product.exceptions.products;

import com.ucc.product.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
    }
} 