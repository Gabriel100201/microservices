package com.ucc.product.exceptions.categories;

import com.ucc.product.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND");
    }
} 