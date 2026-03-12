package com.eren.storeapi.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "inventories",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_store_product", columnNames = {"store_id", "product_id"})
        }
)
public class Inventory {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Version
    @Column(nullable = false)
    private Long version;

    public Inventory() {
    }

    public Inventory(UUID id, Store store, Product product, Integer quantity) {
        this.id = id;
        this.store = store;
        this.product = product;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}