package br.com.e_commerce.Zenk.controller;

import br.com.e_commerce.Zenk.dtos.request.ProdutoRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.ProdutoResponseCategoriaResponseDTO;
import br.com.e_commerce.Zenk.dtos.response.ProdutoResponseDTO;
import br.com.e_commerce.Zenk.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    //OK
    @PostMapping("/categorias/{categoriaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createProduto(@Valid @RequestBody ProdutoRequestDTO dto,
                              @PathVariable Integer categoriaId) throws Exception{
        produtoService.createProduto(dto, categoriaId);
    }

    //OK
    @GetMapping("/categorias/{categoriaId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProdutoResponseDTO> findAllByCategoriaId(@PathVariable Integer categoriaId,
                                                         @PageableDefault Pageable pageable) throws Exception {
        return produtoService.findAllByCategoriaId(categoriaId, pageable);
    }

    //OK
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProdutoResponseCategoriaResponseDTO> findAllProdutos(@PageableDefault Pageable pageable) {
        return produtoService.findAll(pageable);
    }

    //OK
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO findProdutoById(@PathVariable Integer id) throws Exception {
        return produtoService.findById(id);
    }

    //OK
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateProduto (@PathVariable Integer id, @Valid @RequestBody ProdutoRequestDTO dto) throws Exception{
        produtoService.update(id, dto);
    }

    // OK
    @PatchMapping("/{produtoId}/categorias/{categoriaId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategoriaProduto(@PathVariable Integer produtoId, @PathVariable Integer categoriaId) throws Exception {
        produtoService.updateCategoria(produtoId, categoriaId);
    }

    //OK
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduto(@PathVariable Integer id) throws Exception{
        produtoService.delete(id);
    }
}
