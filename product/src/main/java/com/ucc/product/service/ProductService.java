package com.ucc.product.service;

import com.ucc.product.exceptions.products.ProductNotFoundException;
import com.ucc.product.exceptions.products.ProductValidationException;
import com.ucc.product.exceptions.categories.CategoryNotFoundException;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.entities.Category;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.model.dto.ProductinfoDTO;
import com.ucc.product.model.mappers.ProductsMapper;
import com.ucc.product.repository.ProductRepository;
import com.ucc.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductsMapper productsMapper;
    private final CategoryRepository categoryRepository;

    public List<ProductinfoDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No hay productos disponibles");
        }
        return products.stream()
                .map(productEntity -> new ProductinfoDTO(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productEntity.getCategory()))
                .collect(Collectors.toList());
    }

    public Product getProductByID(Long id) {
        if (id == null || id <= 0) {
            throw new ProductValidationException("El ID del producto debe ser un número positivo");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se encontró el producto con el ID: " + id));
    }

    public void createProduct(ProductDTO productDTO) {
        validateProductDTO(productDTO);
        Product productEntity = productsMapper.productDTOToProductsEntity(productDTO);
        productRepository.save(productEntity);
    }

    public void deleteProductByID(Long id) {
        if (id == null || id <= 0) {
            throw new ProductValidationException("El ID del producto debe ser un número positivo");
        }
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("No se encontró el producto con el ID: " + id);
        }
        productRepository.deleteById(id);
    }

    public void editProductByID(ProductDTO productDTO, Long id) {
        if (id == null || id <= 0) {
            throw new ProductValidationException("El ID del producto debe ser un número positivo");
        }
        if (productDTO == null) {
            throw new ProductValidationException("El producto no puede ser nulo");
        }
        
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se encontró el producto con el ID: " + id));

        validateProductDTO(productDTO);
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setPrice(productDTO.getPrice());
        
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("No se encontró la categoría con el ID: " + productDTO.getCategoryId()));
            existingProduct.setCategory(category);
        }

        productRepository.save(existingProduct);
    }

    public List<Product> getProductsByStatusTrue() {
        List<Product> products = productRepository.findAllByStatusTrue();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No hay productos activos disponibles");
        }
        return products;
    }

    public void updateStock(Long productId, Integer quantity) {
        if (productId == null || productId <= 0) {
            throw new ProductValidationException("El ID del producto debe ser un número positivo");
        }
        if (quantity == null) {
            throw new ProductValidationException("La cantidad no puede ser nula");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No se encontró el producto con el ID: " + productId));

        Integer newStock = product.getStock() + quantity;
        if (newStock < 0) {
            throw new ProductValidationException("No hay suficiente stock disponible");
        }

        product.setStock(newStock);
        productRepository.save(product);
    }

    public Integer getProductStock(Long productId) {
        if (productId == null || productId <= 0) {
            throw new ProductValidationException("El ID del producto debe ser un número positivo");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No se encontró el producto con el ID: " + productId));

        return product.getStock();
    }

    @Transactional
    public void updateProductStock(Long id, Integer quantity) {
        Product product = getProductByID(id);
        
        // Si la cantidad es negativa, estamos restaurando stock
        if (quantity < 0) {
            product.setStock(product.getStock() - quantity); // Restamos un número negativo = sumamos
        } else {
            // Si la cantidad es positiva, verificamos que haya stock suficiente
            if (product.getStock() < quantity) {
                throw new ProductValidationException(
                    String.format("Stock insuficiente. Disponible: %d, Solicitado: %d", 
                        product.getStock(), quantity)
                );
            }
            product.setStock(product.getStock() - quantity);
        }
        
        productRepository.save(product);
    }

    private void validateProductDTO(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new ProductValidationException("El producto no puede ser nulo");
        }
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new ProductValidationException("El nombre del producto no puede estar vacío");
        }
        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            throw new ProductValidationException("El precio del producto debe ser mayor que cero");
        }
        if (productDTO.getStock() == null || productDTO.getStock() < 0) {
            throw new ProductValidationException("El stock no puede ser negativo");
        }
        if (productDTO.getCategoryId() == null || productDTO.getCategoryId() <= 0) {
            throw new ProductValidationException("El ID de la categoría debe ser un número positivo");
        }
    }

    private void validateProductForUpdate(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ProductValidationException("El nombre del producto no puede estar vacío");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new ProductValidationException("El precio del producto debe ser mayor que cero");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new ProductValidationException("El stock no puede ser negativo");
        }
    }
}
