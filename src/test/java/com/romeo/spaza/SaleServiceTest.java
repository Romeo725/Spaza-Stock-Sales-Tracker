package com.romeo.spaza;

import com.romeo.spaza.dto.SaleRequest;
import com.romeo.spaza.dto.SaleResponse;
import com.romeo.spaza.exception.InsufficientStockException;
import com.romeo.spaza.model.Product;
import com.romeo.spaza.model.Sale;
import com.romeo.spaza.repository.ProductRepository;
import com.romeo.spaza.repository.SaleRepository;
import com.romeo.spaza.service.AuthService;
import com.romeo.spaza.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private SaleService saleService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Cooking Oil 750ml")
                .price(new BigDecimal("32.00"))
                .quantity(10)
                .reorderLevel(3)
                .build();
    }

    @Test
    void testRecordSale_Success() {
        SaleRequest request = new SaleRequest();
        request.setProductId(1L);
        request.setQuantity(2);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> {
            Sale s = invocation.getArgument(0);
            s.setId(100L);
            return s;
        });

        SaleResponse response = saleService.recordSale(request);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(8, product.getQuantity()); // Stock deducted from 10 to 8
        assertEquals(new BigDecimal("64.00"), response.getTotalPrice()); // 2 * 32.00
    }

    @Test
    void testRecordSale_InsufficientStock_ThrowsException() {
        SaleRequest request = new SaleRequest();
        request.setProductId(1L);
        request.setQuantity(15); // Product has 10

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> saleService.recordSale(request));
        verify(saleRepository, never()).save(any(Sale.class));
    }
}
