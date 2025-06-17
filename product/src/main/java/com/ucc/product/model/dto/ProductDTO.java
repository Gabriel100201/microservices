package com.ucc.product.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

public class ProductDTO implements Serializable {
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private CategoryDTO categoryDTO;
}
