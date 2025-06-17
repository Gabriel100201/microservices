package com.ucc.product.model.mappers;

import com.ucc.product.model.dto.CategoryDTO;
import com.ucc.product.model.dto.CategoryResponseDTO;
import com.ucc.product.model.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    
    public Category categoryDTOToCategory(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
    
    public CategoryResponseDTO categoryToCategoryResponseDTO(Category category) {
        if (category == null) {
            return null;
        }
        
        return new CategoryResponseDTO(
            category.getId(),
            category.getName()
        );
    }
} 