package com.api.bluevelvet_music_store.service;

import com.api.bluevelvet_music_store.dtos.ProductDto;
import com.api.bluevelvet_music_store.mappers.ProductMapper;
import com.api.bluevelvet_music_store.models.DetailsModel;
import com.api.bluevelvet_music_store.models.DimensionsModel;
import com.api.bluevelvet_music_store.models.ImageModel;
import com.api.bluevelvet_music_store.models.ProductModel;
import com.api.bluevelvet_music_store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    final ProductRepository productRepository;
    final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public boolean existsByProductName(String name){
        return productRepository.existsByProductName(name);
    }

    public Page<ProductModel> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductModel> findById(Long id){
        return productRepository.findById(id);
    }

    public Page<ProductModel> searchProdutos(String name, Pageable pageable){
        return productRepository.searchProdutos(name,pageable);
    }

    public ProductModel toModel(ProductDto productDto){
        return productMapper.toModel(productDto);
    }

    @Transactional
    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }

    @Transactional
    public void productInitializer() {
        if (productRepository.count() > 0) {
            productRepository.deleteAll();
        }

        for (int i = 0; i < 10; i++) {
            ProductModel product = new ProductModel();
            String imgData = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAHCAYAAAAvZezQAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYSURBVBhXY+Tn5//PgASYoDQc0ECAgQEAitYBOhOAU/4AAAAASUVORK5CYII=";

            product.setProductName("Exemplo de Produto " + i);
            product.setShortDescription("Descrição curta do produto " + i + ".");
            product.setFullDescription("Descrição completa do produto " + i + ", incluindo detalhes importantes.");
            product.setBrand("Marca Exemplo");
            product.setCategory("Categoria Exemplo");
            product.setMainImage(imgData);

            List<ImageModel> featuredImages = new ArrayList<>();

            ImageModel image1 = new ImageModel();
            image1.setImages(imgData);
            featuredImages.add(image1);

            ImageModel image2 = new ImageModel();
            image2.setImages(imgData);
            featuredImages.add(image2);

            product.setFeaturedImages(featuredImages);

            product.setPrice(BigDecimal.valueOf(199.99));
            product.setDiscount(BigDecimal.valueOf(19.99));
            product.setEnabled(true);
            product.setInStock(true);

            DimensionsModel dimensions = new DimensionsModel();
            dimensions.setLength(BigDecimal.valueOf(30.0));
            dimensions.setWidth(BigDecimal.valueOf(10.0));
            dimensions.setHeight(BigDecimal.valueOf(20.0));
            dimensions.setWeight(BigDecimal.valueOf(1.5));
            dimensions.setUnit("cm");
            dimensions.setUnitWeight("kg");
            product.setDimensions(dimensions);

            List<DetailsModel> details = new ArrayList<>();

            DetailsModel detail1 = new DetailsModel();
            detail1.setName("Cor");
            detail1.setValue("Vermelho");

            DetailsModel detail2 = new DetailsModel();
            detail2.setName("Tamanho");
            detail2.setValue("M");

            details.add(detail1);
            details.add(detail2);

            product.setDetails(details);

            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
            product.setCreatedAt(now);
            product.setUpdateAt(now);

            try {
                productRepository.save(product);
            } catch (Exception e) {
                System.err.println("Erro ao salvar o produto " + product.getProductName() +
                        ": " + e.getMessage());
            }
        }
    }

}
