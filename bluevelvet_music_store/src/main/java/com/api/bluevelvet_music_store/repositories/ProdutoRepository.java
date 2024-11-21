package com.api.bluevelvet_music_store.repositories;

import com.api.bluevelvet_music_store.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel,Long> {
    boolean existsByProductName(String name);
    List<ProdutoModel> findByProductNameContainingIgnoreCase(String name);
}