package com.api.bluevelvet_music_store.controllers;

import com.api.bluevelvet_music_store.dtos.ProdutoDto;
import com.api.bluevelvet_music_store.models.ProdutoModel;
import com.api.bluevelvet_music_store.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/produtos")
public class ProdutoController {

    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveProdutos(@RequestBody @Valid ProdutoDto produtoDto){
        if(produtoService.existsByProductName(produtoDto.productName())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflito: o nome desse produto já está em uso!");
        }
        var produtoModel = produtoService.handleSave(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoModel));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoModel>> getAllProdutos(@PageableDefault(sort = "idProduto", direction = Sort.Direction.ASC)
                                                             Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProdutoModel>> getProdutosByName(@RequestParam String produto){
        List<ProdutoModel> produtoModels = produtoService.findByProductNameContainingIgnoreCase(produto);
        if(produtoModels.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(produtoModels);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduto(@PathVariable(value = "id") Long id){
        Optional<ProdutoModel> produtoModelOpt = produtoService.findById(id);
        if(produtoModelOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoModelOpt.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduto(@PathVariable(value = "id") Long id,
                                                @RequestBody @Valid ProdutoDto produtoDto){
        Optional<ProdutoModel> produtoModelOpt = produtoService.findById(id);
        if(produtoModelOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        var produtoModel = produtoService.handleUpdate(produtoModelOpt.get(),produtoDto);
        produtoModel.setIdProduto(produtoModelOpt.get().getIdProduto());
        produtoModel.setCreatedAt(produtoModelOpt.get().getCreatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.save(produtoModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable(value = "id") Long id){
        Optional<ProdutoModel> produtoModelOpt = produtoService.findById(id);
        if(produtoModelOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        produtoService.delete(produtoModelOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

}