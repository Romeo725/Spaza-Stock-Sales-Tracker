package com.romeo.spaza.dto;

import java.math.BigDecimal;

public class RestockSuggestionDto {
    private Long productId;
    private String productName;
    private String category;
    private Integer currentStock;
    private Integer reorderLevel;
    private Integer recommendedOrderQuantity;
    private BigDecimal unitPrice;
    private BigDecimal estimatedRestockCost;
    private String supplierName;
    private String urgency;

    public RestockSuggestionDto() {}

    public RestockSuggestionDto(Long productId, String productName, String category, Integer currentStock, Integer reorderLevel, Integer recommendedOrderQuantity, BigDecimal unitPrice, BigDecimal estimatedRestockCost, String supplierName, String urgency) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.currentStock = currentStock;
        this.reorderLevel = reorderLevel;
        this.recommendedOrderQuantity = recommendedOrderQuantity;
        this.unitPrice = unitPrice;
        this.estimatedRestockCost = estimatedRestockCost;
        this.supplierName = supplierName;
        this.urgency = urgency;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }

    public Integer getRecommendedOrderQuantity() { return recommendedOrderQuantity; }
    public void setRecommendedOrderQuantity(Integer recommendedOrderQuantity) { this.recommendedOrderQuantity = recommendedOrderQuantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getEstimatedRestockCost() { return estimatedRestockCost; }
    public void setEstimatedRestockCost(BigDecimal estimatedRestockCost) { this.estimatedRestockCost = estimatedRestockCost; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public static RestockSuggestionDtoBuilder builder() { return new RestockSuggestionDtoBuilder(); }

    public static class RestockSuggestionDtoBuilder {
        private Long productId;
        private String productName;
        private String category;
        private Integer currentStock;
        private Integer reorderLevel;
        private Integer recommendedOrderQuantity;
        private BigDecimal unitPrice;
        private BigDecimal estimatedRestockCost;
        private String supplierName;
        private String urgency;

        public RestockSuggestionDtoBuilder productId(Long productId) { this.productId = productId; return this; }
        public RestockSuggestionDtoBuilder productName(String productName) { this.productName = productName; return this; }
        public RestockSuggestionDtoBuilder category(String category) { this.category = category; return this; }
        public RestockSuggestionDtoBuilder currentStock(Integer currentStock) { this.currentStock = currentStock; return this; }
        public RestockSuggestionDtoBuilder reorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; return this; }
        public RestockSuggestionDtoBuilder recommendedOrderQuantity(Integer recommendedOrderQuantity) { this.recommendedOrderQuantity = recommendedOrderQuantity; return this; }
        public RestockSuggestionDtoBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public RestockSuggestionDtoBuilder estimatedRestockCost(BigDecimal estimatedRestockCost) { this.estimatedRestockCost = estimatedRestockCost; return this; }
        public RestockSuggestionDtoBuilder supplierName(String supplierName) { this.supplierName = supplierName; return this; }
        public RestockSuggestionDtoBuilder urgency(String urgency) { this.urgency = urgency; return this; }

        public RestockSuggestionDto build() {
            return new RestockSuggestionDto(productId, productName, category, currentStock, reorderLevel, recommendedOrderQuantity, unitPrice, estimatedRestockCost, supplierName, urgency);
        }
    }
}
