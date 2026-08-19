package br.com.e_commerce.Zenk.dtos.response;

import java.math.BigDecimal;

public record ProdutoResponseCategoriaResponseDTO(
        Integer id,
        String nome,
        String descricao,
        BigDecimal preco,
        int quantidade,
        String nomeCategoria
)
{}
