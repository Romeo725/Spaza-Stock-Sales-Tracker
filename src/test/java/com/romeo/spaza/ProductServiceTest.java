package com.romeo.spaza;

import com.romeo.spaza.dto.ProductDto;
import com.romeo.spaza.dto.RestockSuggestionDto;
import com.romeo.spaza.model.Product;
import com.romeo.spaza.repository.ProductRepository;
import com.romeo.spaza.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("White Bread")
                .category("Bakery")
                .price(new BigDecimal("17.50"))
                .quantity(3)
                .reorderLevel(5)
                .supplierName("Albany Bakery")
                .build();
    }

    @Test
    void testGetLowStockProducts() {
        when(productRepository.findLowStockProducts()).thenReturn(List.of(testProduct));

        List<ProductDto> result = productService.getLowStockProducts();

        assertEquals(1, result.size());
        assertEquals("White Bread", result.get(0).getName());
        assertTrue(result.get(0).isLowStock());
    }

    @Test
    void testGetRestockSuggestions() {
        when(productRepository.findLowStockProducts()).thenReturn(List.of(testProduct));

        List<RestockSuggestionDto> suggestions = productService.getRestockSuggestions();

        assertEquals(1, suggestions.size());
        RestockSuggestionDto suggestion = suggestions.get(0);
        assertEquals(1L, suggestion.getProductId());
        // Target = max(reorderLevel * 2, 10) = 10. Recommended = 10 - 3 = 7
        assertEquals(7, suggestion.getRecommendedOrderQuantity());
        assertEquals(new BigDecimal("122.50"), suggestion.getEstimatedRestockCost()); // 7 * 17.50 = 122.50
    }

    @Test
    void testCreateProduct() {
        ProductDto dto = ProductDto.builder()
                .name("Milk 2L")
                .category("Dairy")
                .price(new BigDecimal("34.90"))
                .quantity(10)
                .reorderLevel(5)
                .build();

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(2L);
            return p;
        });

        ProductDto created = productService.createProduct(dto);

        assertNotNull(created.getId());
        assertEquals("Milk 2L", created.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
