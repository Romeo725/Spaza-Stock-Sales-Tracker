package com.romeo.spaza.dto;

import java.math.BigDecimal;

public class DashboardSummaryDto {
    private BigDecimal totalRevenue;
    private Long totalSalesCount;
    private Long totalProductsCount;
    private Long lowStockCount;
    private BigDecimal totalInventoryValue;
    private Integer itemsToRestockCount;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(BigDecimal totalRevenue, Long totalSalesCount, Long totalProductsCount, Long lowStockCount, BigDecimal totalInventoryValue, Integer itemsToRestockCount) {
        this.totalRevenue = totalRevenue;
        this.totalSalesCount = totalSalesCount;
        this.totalProductsCount = totalProductsCount;
        this.lowStockCount = lowStockCount;
        this.totalInventoryValue = totalInventoryValue;
        this.itemsToRestockCount = itemsToRestockCount;
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getTotalSalesCount() { return totalSalesCount; }
    public void setTotalSalesCount(Long totalSalesCount) { this.totalSalesCount = totalSalesCount; }

    public Long getTotalProductsCount() { return totalProductsCount; }
    public void setTotalProductsCount(Long totalProductsCount) { this.totalProductsCount = totalProductsCount; }

    public Long getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(Long lowStockCount) { this.lowStockCount = lowStockCount; }

    public BigDecimal getTotalInventoryValue() { return totalInventoryValue; }
    public void setTotalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; }

    public Integer getItemsToRestockCount() { return itemsToRestockCount; }
    public void setItemsToRestockCount(Integer itemsToRestockCount) { this.itemsToRestockCount = itemsToRestockCount; }

    public static DashboardSummaryDtoBuilder builder() { return new DashboardSummaryDtoBuilder(); }

    public static class DashboardSummaryDtoBuilder {
        private BigDecimal totalRevenue;
        private Long totalSalesCount;
        private Long totalProductsCount;
        private Long lowStockCount;
        private BigDecimal totalInventoryValue;
        private Integer itemsToRestockCount;

        public DashboardSummaryDtoBuilder totalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; return this; }
        public DashboardSummaryDtoBuilder totalSalesCount(Long totalSalesCount) { this.totalSalesCount = totalSalesCount; return this; }
        public DashboardSummaryDtoBuilder totalProductsCount(Long totalProductsCount) { this.totalProductsCount = totalProductsCount; return this; }
        public DashboardSummaryDtoBuilder lowStockCount(Long lowStockCount) { this.lowStockCount = lowStockCount; return this; }
        public DashboardSummaryDtoBuilder totalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; return this; }
        public DashboardSummaryDtoBuilder itemsToRestockCount(Integer itemsToRestockCount) { this.itemsToRestockCount = itemsToRestockCount; return this; }

        public DashboardSummaryDto build() {
            return new DashboardSummaryDto(totalRevenue, totalSalesCount, totalProductsCount, lowStockCount, totalInventoryValue, itemsToRestockCount);
        }
    }
}
