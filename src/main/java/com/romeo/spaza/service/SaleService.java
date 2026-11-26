package com.romeo.spaza.service;

import com.romeo.spaza.dto.SaleRequest;
import com.romeo.spaza.dto.SaleResponse;
import com.romeo.spaza.exception.InsufficientStockException;
import com.romeo.spaza.exception.ResourceNotFoundException;
import com.romeo.spaza.model.Product;
import com.romeo.spaza.model.Sale;
import com.romeo.spaza.model.User;
import com.romeo.spaza.repository.ProductRepository;
import com.romeo.spaza.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, AuthService authService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.authService = authService;
    }

    @Transactional
    public SaleResponse recordSale(SaleRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Sale request cannot be null");
        }
        if (request.getProductId() == null) {
            throw new ResourceNotFoundException("Product id is required");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Sale quantity must be greater than zero");
        }

        Product product = productRepository.findByIdForUpdate(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        if (product.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(
                    String.format("Insufficient stock for '%s'. Available: %d, Requested: %d",
                            product.getName(), product.getQuantity(), request.getQuantity())
            );
        }

        product.setQuantity(product.getQuantity() - request.getQuantity());
        productRepository.save(product);

        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        User currentUser = authService.getCurrentUser();
        String recordedBy = currentUser != null ? currentUser.getUsername() : "system";

        Sale sale = Sale.builder()
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .saleDate(LocalDateTime.now())
                .recordedByUsername(recordedBy)
                .build();

        Sale savedSale = saleRepository.save(sale);

        return SaleResponse.builder()
                .id(savedSale.getId())
                .productId(product.getId())
                .productName(product.getName())
                .quantity(savedSale.getQuantity())
                .unitPrice(savedSale.getUnitPrice())
                .totalPrice(savedSale.getTotalPrice())
                .saleDate(savedSale.getSaleDate())
                .recordedByUsername(savedSale.getRecordedByUsername())
                .remainingStock(product.getQuantity())
                .build();
    }

    public List<SaleResponse> getAllSales() {
        return saleRepository.findAllByOrderBySaleDateDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SaleResponse mapToResponse(Sale sale) {
        return SaleResponse.builder()
                .id(sale.getId())
                .productId(sale.getProduct() != null ? sale.getProduct().getId() : null)
                .productName(sale.getProduct() != null ? sale.getProduct().getName() : "Deleted Product")
                .quantity(sale.getQuantity())
                .unitPrice(sale.getUnitPrice())
                .totalPrice(sale.getTotalPrice())
                .saleDate(sale.getSaleDate())
                .recordedByUsername(sale.getRecordedByUsername())
                .remainingStock(sale.getProduct() != null ? sale.getProduct().getQuantity() : 0)
                .build();
    }
}
