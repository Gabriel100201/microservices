package com.ucc.product.controller;

import com.ucc.product.model.dto.CategoryDTO;
import com.ucc.product.model.dto.CategoryResponseDTO;
import com.ucc.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorías", description = "API para la gestión de categorías de productos")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Obtener todas las categorías", description = "Retorna una lista de todas las categorías disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "No se encontraron categorías")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDTO> getCategories() {
        return categoryService.getCategories();
    }

    @Operation(summary = "Obtener categoría por ID", description = "Retorna una categoría específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO getCategoryById(@Parameter(description = "ID de la categoría") @PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la categoría inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO createCategory(@Parameter(description = "Datos de la categoría") @RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de la categoría inválidos")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO updateCategory(
            @Parameter(description = "ID de la categoría") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la categoría") @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@Parameter(description = "ID de la categoría") @PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
