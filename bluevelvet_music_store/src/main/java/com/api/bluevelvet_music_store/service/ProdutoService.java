
package com.api.bluevelvet_music_store.service;

import com.api.bluevelvet_music_store.dtos.ProdutoDto;
import com.api.bluevelvet_music_store.models.DimensoesModel;
import com.api.bluevelvet_music_store.models.ImagemModel;
import com.api.bluevelvet_music_store.models.ProdutoModel;
import com.api.bluevelvet_music_store.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoModel save(ProdutoModel produtoModel) {
        return produtoRepository.save(produtoModel);
    }

    public ProdutoModel handleSave(ProdutoDto produtoDto){
        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoDto, produtoModel,"dimensions","images");

        var dimensoesModel = new DimensoesModel();
        BeanUtils.copyProperties(produtoDto.dimensions(), dimensoesModel);

        produtoDto.images().forEach( imgDto -> {
            var imagemModel = new ImagemModel();
            BeanUtils.copyProperties(imgDto, imagemModel);
            produtoModel.getImagens().add(imagemModel);
            imagemModel.setProduto(produtoModel);
        });

        produtoModel.setDimensoes(dimensoesModel);
        dimensoesModel.setProduto(produtoModel);

        produtoModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        produtoModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));

        return produtoModel;
    }

    public ProdutoModel handleUpdate(ProdutoModel produtoModel, ProdutoDto produtoDto){

        BeanUtils.copyProperties(produtoDto, produtoModel,"dimensions","images");

        var dimensoesModel = produtoModel.getDimensoes();
        BeanUtils.copyProperties(produtoDto.dimensions(), dimensoesModel);

        List<ImagemModel> imgsExists = new ArrayList<>(produtoModel.getImagens());
        produtoModel.getImagens().clear();

        produtoDto.images().forEach( imgDto -> {

            boolean exists = imgsExists.stream()
                    .anyMatch(imgEqual -> imgEqual.getImgData().equals(imgDto.imgData()));

            if(!exists){
                var imagemModel = new ImagemModel();
                BeanUtils.copyProperties(imgDto, imagemModel);
                imagemModel.setProduto(produtoModel);
                produtoModel.getImagens().add(imagemModel);
            } else {
                imgsExists.stream()
                        .filter(imgMain -> imgMain.getImgData().equals(imgDto.imgData()))
                        .forEach(imgMain -> imgMain.setImgMain(imgDto.isImgMain()));
            }
        });

        produtoModel.setDimensoes(dimensoesModel);
        dimensoesModel.setProduto(produtoModel);

        produtoModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));

        return produtoModel;
    }

    public boolean existsByProductName(String name){
        return produtoRepository.existsByProductName(name);
    }

    public Page<ProdutoModel> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Optional<ProdutoModel> findById(Long id){
        return produtoRepository.findById(id);
    }

    public Page<ProdutoModel> searchProdutos(String name, Pageable pageable){
        return produtoRepository.searchProdutos(name,pageable);
    }

    @Transactional
    public void delete(ProdutoModel produtoModel) {
        produtoRepository.delete(produtoModel);
    }


}
