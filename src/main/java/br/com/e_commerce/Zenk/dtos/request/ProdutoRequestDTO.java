package br.com.e_commerce.Zenk.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull BigDecimal preco,
        @NotNull int quantidade
) {
}
