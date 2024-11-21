
package com.api.bluevelvet_music_store.service;

import com.api.bluevelvet_music_store.models.ProdutoModel;
import com.api.bluevelvet_music_store.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public boolean existsByProductName(String name){
        return produtoRepository.existsByProductName(name);
    }

    public Page<ProdutoModel> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Optional<ProdutoModel> findById(Long id){
        return produtoRepository.findById(id);
    }

    public List<ProdutoModel> findByProductNameContainingIgnoreCase(String name){
        return produtoRepository.findByProductNameContainingIgnoreCase(name);
    }

    @Transactional
    public void delete(ProdutoModel produtoModel) {
        produtoRepository.delete(produtoModel);
    }


}
