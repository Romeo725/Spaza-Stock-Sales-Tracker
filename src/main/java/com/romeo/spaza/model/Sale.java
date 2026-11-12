package com.romeo.spaza.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    private String recordedByUsername;

    public Sale() {}

    public Sale(Long id, Product product, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice, LocalDateTime saleDate, String recordedByUsername) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.recordedByUsername = recordedByUsername;
    }

    @PrePersist
    protected void onCreate() {
        if (this.saleDate == null) {
            this.saleDate = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

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

    public static SaleBuilder builder() { return new SaleBuilder(); }

    public static class SaleBuilder {
        private Long id;
        private Product product;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private LocalDateTime saleDate;
        private String recordedByUsername;

        public SaleBuilder id(Long id) { this.id = id; return this; }
        public SaleBuilder product(Product product) { this.product = product; return this; }
        public SaleBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public SaleBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public SaleBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public SaleBuilder saleDate(LocalDateTime saleDate) { this.saleDate = saleDate; return this; }
        public SaleBuilder recordedByUsername(String recordedByUsername) { this.recordedByUsername = recordedByUsername; return this; }

        public Sale build() {
            return new Sale(id, product, quantity, unitPrice, totalPrice, saleDate, recordedByUsername);
        }
    }
}
