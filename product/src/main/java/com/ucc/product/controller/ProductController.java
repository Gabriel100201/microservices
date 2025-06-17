package com.ucc.product.controller;

import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.model.dto.ProductinfoDTO;
import com.ucc.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductinfoDTO> getProducts(){
        return productService.getAllProducts();
    }

    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByID(@Parameter(description = "ID del producto") @PathVariable Long id){
        return productService.getProductByID(id);
    }

    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@Parameter(description = "Datos del producto") @RequestBody ProductDTO product){
        this.productService.createProduct(product);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@Parameter(description = "ID del producto") @PathVariable Long id) {
        this.productService.deleteProductByID(id);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editProductByID(
            @Parameter(description = "Datos a actualizar del producto") @RequestBody ProductDTO productDTO,
            @Parameter(description = "ID del producto a actualizar") @PathVariable Long id){
        this.productService.editProductByID(productDTO, id);
    }

    @Operation(summary = "Obtener productos activos", description = "Retorna una lista de todos los productos con estado activo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos activos")
    })
    @GetMapping("/true")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByStatusTrue() {
        return productService.getProductsByStatusTrue();
    }

    @Operation(summary = "Obtener stock de producto", description = "Retorna el stock disponible de un producto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.OK)
    public Integer getProductStock(@Parameter(description = "ID del producto") @PathVariable Long id) {
        return productService.getProductStock(id);
    }

    @Operation(summary = "Obtener precio de producto", description = "Retorna el precio de un producto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/price")
    @ResponseStatus(HttpStatus.OK)
    public Double getProductPrice(@Parameter(description = "ID del producto") @PathVariable Long id) {
        return productService.getProductByID(id).getPrice();
    }
}
