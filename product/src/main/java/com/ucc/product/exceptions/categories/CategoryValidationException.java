package com.ucc.product.exceptions.categories;

import com.ucc.product.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryValidationException extends BaseException {
    public CategoryValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "CATEGORY_VALIDATION_ERROR");
    }
} 