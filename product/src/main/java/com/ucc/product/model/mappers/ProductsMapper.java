package com.ucc.product.model.mappers;

import com.ucc.product.model.entities.Category;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductsMapper {

    private final CategoryRepository categoryRepository;

    public Product productDTOToProductsEntity(ProductDTO productDTO) {
        Product productEntity = new Product();
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setStatus(Boolean.TRUE);
        productEntity.setStock(productDTO.getStock());
        productEntity.setDescription(productDTO.getDescription());

        Category categoryEntity = categoryRepository.findOneById(productDTO.getCategoryDTO().getId());
        productEntity.setCategory(categoryEntity);
        return productEntity;
    }

    public ProductDTO productEnityToProductsDTO (Product productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice());
        return productDTO;
    }
}
