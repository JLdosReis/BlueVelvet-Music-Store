package com.api.bluevelvet_music_store.mappers;

import com.api.bluevelvet_music_store.dtos.DetailsDto;
import com.api.bluevelvet_music_store.dtos.DimensionsDto;
import com.api.bluevelvet_music_store.dtos.ImageDto;
import com.api.bluevelvet_music_store.dtos.ProductDto;
import com.api.bluevelvet_music_store.models.DimensionsModel;
import com.api.bluevelvet_music_store.models.ImageModel;
import com.api.bluevelvet_music_store.models.ProductModel;
import com.api.bluevelvet_music_store.models.DetailsModel;
import com.api.bluevelvet_music_store.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    final ProductRepository productRepository;

    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel toModel(ProductDto productDto){
        var productModel = new ProductModel();

        BeanUtils.copyProperties(productDto, productModel, "dimensions", "mainImage", "featuredImages", "details");
        productModel.setMainImage(productDto.mainImage());

        DimensionsModel dimensions = mapDimensions(productDto.dimensions(), productModel.getDimensions());
        List<ImageModel> images = mapImages(productDto.featuredImages());
        List<DetailsModel> details = mapDetails(productDto.details());

        productModel.setDimensions(dimensions);
        productModel.setFeaturedImages(images);
        productModel.setDetails(details);

        if (!productRepository.existsByIdProduct(productModel.getIdProduct())) {
            productModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        }

        productModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));

        return productModel;
    }


    private DimensionsModel mapDimensions(DimensionsDto dimensionsDto, DimensionsModel existingDimensions) {
        DimensionsModel dimensions = (existingDimensions != null) ? existingDimensions : new DimensionsModel();
        if (dimensionsDto != null) {
            BeanUtils.copyProperties(dimensionsDto, dimensions);
        }
        return dimensions;
    }

    private List<ImageModel> mapImages(List<ImageDto> imageDtos) {
        return imageDtos.stream()
                .map(img -> {
                    ImageModel image = new ImageModel();
                    image.setImages(img.image());
                    return image;
                })
                .collect(Collectors.toList());
    }

    private List<DetailsModel> mapDetails(List<DetailsDto> detailsDtos) {
        return detailsDtos.stream()
                .map(detailsDto -> {
                    DetailsModel detail = new DetailsModel();
                    BeanUtils.copyProperties(detailsDto, detail);
                    return detail;
                })
                .collect(Collectors.toList());
    }
}
