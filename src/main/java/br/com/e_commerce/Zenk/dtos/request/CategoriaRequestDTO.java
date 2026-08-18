package br.com.e_commerce.Zenk.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank String nome,
        String descricao
) {
}
