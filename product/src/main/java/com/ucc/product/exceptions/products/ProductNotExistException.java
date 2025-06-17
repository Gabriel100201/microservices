package com.ucc.product.exceptions.products;

import com.ucc.product.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotExistException extends BaseException {
    public ProductNotExistException(String message) {
        super(message, HttpStatus.NOT_FOUND, "PRODUCT_NOT_EXIST");
    }
}
