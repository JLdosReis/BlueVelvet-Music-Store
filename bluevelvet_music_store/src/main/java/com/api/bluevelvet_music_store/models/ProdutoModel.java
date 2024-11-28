package com.api.bluevelvet_music_store.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_BLUEVELVET")
public class ProdutoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduto;

    private String productName;
    private String brand;
    private String category;
    private BigDecimal price;
    private BigDecimal discount;

    @Column(columnDefinition = "TINYINT")
    private boolean enabled;
    @Column(columnDefinition = "TINYINT")
    private boolean inStock;

    @Column(length = 100)
    private String shortDescription;
    @Column(length = 500)
    private String fullDescription;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL)
    private DimensoesModel dimensoes;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ImagemModel> imagens = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public DimensoesModel getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(DimensoesModel dimensoes) {
        this.dimensoes = dimensoes;
    }

    public List<ImagemModel> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemModel> imagens) {
        this.imagens = imagens;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}


