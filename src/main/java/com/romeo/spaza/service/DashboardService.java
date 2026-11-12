package com.romeo.spaza.service;

import com.romeo.spaza.dto.DashboardSummaryDto;
import com.romeo.spaza.model.Product;
import com.romeo.spaza.repository.ProductRepository;
import com.romeo.spaza.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public DashboardService(ProductRepository productRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    public DashboardSummaryDto getSummary() {
        BigDecimal totalRevenue = saleRepository.findTotalRevenue();
        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }

        long totalSalesCount = saleRepository.count();
        long totalProductsCount = productRepository.count();

        List<Product> products = productRepository.findAll();
        long lowStockCount = products.stream().filter(Product::isLowStock).count();

        BigDecimal totalInventoryValue = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardSummaryDto.builder()
                .totalRevenue(totalRevenue)
                .totalSalesCount(totalSalesCount)
                .totalProductsCount(totalProductsCount)
                .lowStockCount(lowStockCount)
                .totalInventoryValue(totalInventoryValue)
                .itemsToRestockCount((int) lowStockCount)
                .build();
    }
}
