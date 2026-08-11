package br.com.e_commerce.Zenk.dtos.response;

import br.com.e_commerce.Zenk.database.model.UsuarioEntity;

public record UsuarioResponseDTO(
        String nome,
        String email,
        String cpf,
        String telefone,
        boolean activate
) {
}
