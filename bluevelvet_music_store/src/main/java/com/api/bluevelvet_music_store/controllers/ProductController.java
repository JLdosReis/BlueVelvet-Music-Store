package com.api.bluevelvet_music_store.controllers;

import com.api.bluevelvet_music_store.dtos.ProductDto;
import com.api.bluevelvet_music_store.exceptions.ProductConflictException;
import com.api.bluevelvet_music_store.exceptions.ProductNotFoundException;
import com.api.bluevelvet_music_store.exceptions.ProductsNotFoundException;
import com.api.bluevelvet_music_store.models.ProductModel;
import com.api.bluevelvet_music_store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/produtos")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS')")
    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDto productDto){
        if(productService.existsByProductName(productDto.productName())){
            throw new ProductConflictException(productDto.productName());
        }
        var produtoModel = new ProductModel();
        produtoModel = productService.toModel(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(produtoModel));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/reset")
    public ResponseEntity<Object> productInitializer(){
        productService.productInitializer();
        return ResponseEntity.status(HttpStatus.OK).body("Produtos resetados com sucesso.");
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAllProducts(@PageableDefault(sort = "idProduct", direction = Sort.Direction.ASC)
                                                             Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS', 'EDITOR', 'ASSISTENTE', 'GERENTE_ENTREGAS','USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") Long id){
        Optional<ProductModel> produtoModelOpt = productService.findById(id);
        if(produtoModelOpt.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoModelOpt.get());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductModel>> getProductsByParam(@RequestParam String name,
                                                                 @PageableDefault(sort = "idProduct", direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        Page<ProductModel> produtoModels = productService.searchProdutos(name,pageable);
        if(produtoModels.isEmpty()){
            throw new ProductsNotFoundException(name);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoModels);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") Long id,
                                                @RequestBody @Valid ProductDto productDto){
        Optional<ProductModel> produtoModelOpt = productService.findById(id);
        if(produtoModelOpt.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        var produtoModel = produtoModelOpt.get();
        produtoModel = productService.toModel(productDto);
        produtoModel.setIdProduct(produtoModelOpt.get().getIdProduct());
        produtoModel.setCreatedAt(produtoModelOpt.get().getCreatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(produtoModel));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") Long id){
        Optional<ProductModel> produtoModelOpt = productService.findById(id);
        if(produtoModelOpt.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        productService.delete(produtoModelOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

}