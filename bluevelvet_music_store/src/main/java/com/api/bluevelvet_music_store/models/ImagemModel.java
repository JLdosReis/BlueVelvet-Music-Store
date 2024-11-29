package com.api.bluevelvet_music_store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_IMAGENS")
public class ImagemModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imgData;

    @Column(columnDefinition = "TINYTEXT")
    private boolean isImgMain;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "idProduto")
    @JsonIgnore
    private ProdutoModel produto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgData() {
        return imgData;
    }

    public void setImgData(String imgData) {
        this.imgData = imgData;
    }

    public boolean isImgMain() {
        return isImgMain;
    }

    public void setImgMain(boolean imgMain) {
        isImgMain = imgMain;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }
}
