package com.ucc.product.exceptions.products;

import com.ucc.product.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ProductValidationException extends BaseException {
    public ProductValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "PRODUCT_VALIDATION_ERROR");
    }
} 