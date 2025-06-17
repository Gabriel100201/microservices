package com.ucc.product.controller;

import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.model.dto.ProductinfoDTO;
import com.ucc.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductinfoDTO> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByID(@PathVariable Long id){
        return productService.getProductByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductDTO product){
        this.productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        this.productService.deleteProductByID(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editProductByID(@RequestBody Product product, @PathVariable Long id){
        this.productService.editProductByID(product, id);
    }

    @GetMapping("/true")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByStatusTrue() {
        return productService.getProductsByStatusTrue();
    }
}
