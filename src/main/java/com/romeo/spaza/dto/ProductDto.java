package com.romeo.spaza.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Reorder level threshold is required")
    @Min(value = 0, message = "Reorder level cannot be negative")
    private Integer reorderLevel;

    private String barcode;
    private String supplierName;
    private boolean lowStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDto() {}

    public ProductDto(Long id, String name, String category, BigDecimal price, Integer quantity, Integer reorderLevel, String barcode, String supplierName, boolean lowStock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.barcode = barcode;
        this.supplierName = supplierName;
        this.lowStock = lowStock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public boolean isLowStock() { return lowStock; }
    public void setLowStock(boolean lowStock) { this.lowStock = lowStock; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static ProductDtoBuilder builder() { return new ProductDtoBuilder(); }

    public static class ProductDtoBuilder {
        private Long id;
        private String name;
        private String category;
        private BigDecimal price;
        private Integer quantity;
        private Integer reorderLevel;
        private String barcode;
        private String supplierName;
        private boolean lowStock;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductDtoBuilder id(Long id) { this.id = id; return this; }
        public ProductDtoBuilder name(String name) { this.name = name; return this; }
        public ProductDtoBuilder category(String category) { this.category = category; return this; }
        public ProductDtoBuilder price(BigDecimal price) { this.price = price; return this; }
        public ProductDtoBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public ProductDtoBuilder reorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; return this; }
        public ProductDtoBuilder barcode(String barcode) { this.barcode = barcode; return this; }
        public ProductDtoBuilder supplierName(String supplierName) { this.supplierName = supplierName; return this; }
        public ProductDtoBuilder lowStock(boolean lowStock) { this.lowStock = lowStock; return this; }
        public ProductDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ProductDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ProductDto build() {
            return new ProductDto(id, name, category, price, quantity, reorderLevel, barcode, supplierName, lowStock, createdAt, updatedAt);
        }
    }
}
