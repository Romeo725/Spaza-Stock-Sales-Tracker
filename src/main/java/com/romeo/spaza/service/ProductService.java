package com.romeo.spaza.service;

import com.romeo.spaza.dto.ProductDto;
import com.romeo.spaza.dto.RestockSuggestionDto;
import com.romeo.spaza.exception.ResourceNotFoundException;
import com.romeo.spaza.model.Product;
import com.romeo.spaza.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToDto(product);
    }

    public List<ProductDto> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Product data cannot be null");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (dto.getPrice() == null) {
            throw new IllegalArgumentException("Product price is required");
        }
        if (dto.getQuantity() == null || dto.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }

        Product product = Product.builder()
                .name(dto.getName().trim())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .reorderLevel(dto.getReorderLevel() != null ? dto.getReorderLevel() : 5)
                .barcode(dto.getBarcode())
                .supplierName(dto.getSupplierName())
                .build();

        Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        if (dto.getReorderLevel() != null) {
            product.setReorderLevel(dto.getReorderLevel());
        }
        product.setBarcode(dto.getBarcode());
        product.setSupplierName(dto.getSupplierName());

        Product updated = productRepository.save(product);
        return mapToDto(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto restockProduct(Long id, Integer additionalQuantity) {
        if (additionalQuantity == null || additionalQuantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be greater than zero");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setQuantity(product.getQuantity() + additionalQuantity);
        Product updated = productRepository.save(product);
        return mapToDto(updated);
    }

    public List<RestockSuggestionDto> getRestockSuggestions() {
        List<Product> lowStock = productRepository.findLowStockProducts();

        return lowStock.stream().map(p -> {
            int targetQuantity = Math.max(p.getReorderLevel() * 2, 10);
            int recommendedQty = Math.max(targetQuantity - p.getQuantity(), 1);
            BigDecimal estCost = p.getPrice().multiply(BigDecimal.valueOf(recommendedQty));

            String urgency = "WARNING";
            if (p.getQuantity() == 0) {
                urgency = "CRITICAL";
            } else if (p.getQuantity() <= p.getReorderLevel() / 2) {
                urgency = "HIGH";
            }

            return RestockSuggestionDto.builder()
                    .productId(p.getId())
                    .productName(p.getName())
                    .category(p.getCategory())
                    .currentStock(p.getQuantity())
                    .reorderLevel(p.getReorderLevel())
                    .recommendedOrderQuantity(recommendedQty)
                    .unitPrice(p.getPrice())
                    .estimatedRestockCost(estCost)
                    .supplierName(p.getSupplierName() != null ? p.getSupplierName() : "Local Wholesaler")
                    .urgency(urgency)
                    .build();
        }).collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product p) {
        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory())
                .price(p.getPrice())
                .quantity(p.getQuantity())
                .reorderLevel(p.getReorderLevel())
                .barcode(p.getBarcode())
                .supplierName(p.getSupplierName())
                .lowStock(p.isLowStock())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
