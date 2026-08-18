package br.com.e_commerce.Zenk.controller;

import br.com.e_commerce.Zenk.dtos.request.CategoriaRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.CategoriaResponseDTO;
import br.com.e_commerce.Zenk.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createCategoria(@Valid @RequestBody CategoriaRequestDTO dto) throws Exception {
        categoriaService.create(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoriaResponseDTO> findAll(@PageableDefault Pageable pageable) {
        return categoriaService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaResponseDTO findById(Integer id) throws Exception {
        return categoriaService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategoria(@PathVariable Integer id ,@Valid @RequestBody CategoriaRequestDTO dto) throws Exception {
        categoriaService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategoria(@PathVariable Integer id) throws Exception {
        categoriaService.delete(id);
    }


}
