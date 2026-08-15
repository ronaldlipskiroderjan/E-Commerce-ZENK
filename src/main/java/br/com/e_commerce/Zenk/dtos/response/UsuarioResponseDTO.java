package br.com.e_commerce.Zenk.dtos.response;

public record UsuarioResponseDTO(
        Integer id,
        String nome,
        String email,
        String cpf,
        String telefone
) {
}
