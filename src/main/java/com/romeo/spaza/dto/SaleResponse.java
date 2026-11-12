package com.romeo.spaza.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaleResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private LocalDateTime saleDate;
    private String recordedByUsername;
    private Integer remainingStock;

    public SaleResponse() {}

    public SaleResponse(Long id, Long productId, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice, LocalDateTime saleDate, String recordedByUsername, Integer remainingStock) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.recordedByUsername = recordedByUsername;
        this.remainingStock = remainingStock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }

    public String getRecordedByUsername() { return recordedByUsername; }
    public void setRecordedByUsername(String recordedByUsername) { this.recordedByUsername = recordedByUsername; }

    public Integer getRemainingStock() { return remainingStock; }
    public void setRemainingStock(Integer remainingStock) { this.remainingStock = remainingStock; }

    public static SaleResponseBuilder builder() { return new SaleResponseBuilder(); }

    public static class SaleResponseBuilder {
        private Long id;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private LocalDateTime saleDate;
        private String recordedByUsername;
        private Integer remainingStock;

        public SaleResponseBuilder id(Long id) { this.id = id; return this; }
        public SaleResponseBuilder productId(Long productId) { this.productId = productId; return this; }
        public SaleResponseBuilder productName(String productName) { this.productName = productName; return this; }
        public SaleResponseBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public SaleResponseBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public SaleResponseBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public SaleResponseBuilder saleDate(LocalDateTime saleDate) { this.saleDate = saleDate; return this; }
        public SaleResponseBuilder recordedByUsername(String recordedByUsername) { this.recordedByUsername = recordedByUsername; return this; }
        public SaleResponseBuilder remainingStock(Integer remainingStock) { this.remainingStock = remainingStock; return this; }

        public SaleResponse build() {
            return new SaleResponse(id, productId, productName, quantity, unitPrice, totalPrice, saleDate, recordedByUsername, remainingStock);
        }
    }
}
