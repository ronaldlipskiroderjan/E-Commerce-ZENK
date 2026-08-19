package br.com.e_commerce.Zenk.dtos.request;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        int quantidade
) {
}
