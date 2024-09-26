package com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PRICES")
public class PricePersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRICE_ID", columnDefinition = "UUID")
    private UUID priceId;

    @Column(name = "BRAND_ID", columnDefinition = "UUID")
    private UUID brandId;

    @Column(name = "PRODUCT_ID", columnDefinition = "UUID")
    private UUID productId;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CURRENCY")
    private String currency;

    public UUID getPriceId() {
        return priceId;
    }

    public void setPriceId(UUID priceId) {
        this.priceId = priceId;
    }

    public UUID getBrandId() {
        return brandId;
    }

    public void setBrandId(UUID brandId) {
        this.brandId = brandId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
