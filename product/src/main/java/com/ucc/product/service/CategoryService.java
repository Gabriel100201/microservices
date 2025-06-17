package com.ucc.product.service;

import com.ucc.product.exceptions.categories.CategoryNotFoundException;
import com.ucc.product.exceptions.categories.CategoryValidationException;
import com.ucc.product.model.dto.CategoryDTO;
import com.ucc.product.model.dto.CategoryResponseDTO;
import com.ucc.product.model.entities.Category;
import com.ucc.product.model.mappers.CategoryMapper;
import com.ucc.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No hay categorías disponibles");
        }
        return categories.stream()
                .map(categoryMapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        if (id == null || id <= 0) {
            throw new CategoryValidationException("El ID de la categoría debe ser un número positivo");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No se encontró la categoría con el ID: " + id));
        return categoryMapper.categoryToCategoryResponseDTO(category);
    }

    public CategoryResponseDTO createCategory(CategoryDTO categoryDTO) {
        validateCategoryDTO(categoryDTO);
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponseDTO(savedCategory);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        if (id == null || id <= 0) {
            throw new CategoryValidationException("El ID de la categoría debe ser un número positivo");
        }
        if (categoryDTO == null) {
            throw new CategoryValidationException("La categoría no puede ser nula");
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No se encontró la categoría con el ID: " + id));

        validateCategoryDTO(categoryDTO);
        
        existingCategory.setName(categoryDTO.getName());
        
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryResponseDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        if (id == null || id <= 0) {
            throw new CategoryValidationException("El ID de la categoría debe ser un número positivo");
        }
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("No se encontró la categoría con el ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private void validateCategoryDTO(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new CategoryValidationException("La categoría no puede ser nula");
        }
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new CategoryValidationException("El nombre de la categoría no puede estar vacío");
        }
    }
}
