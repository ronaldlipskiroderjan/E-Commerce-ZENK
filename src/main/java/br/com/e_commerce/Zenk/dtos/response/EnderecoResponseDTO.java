package br.com.e_commerce.Zenk.dtos.response;

public record EnderecoResponseDTO(
        Integer id,
        String logradouro,
        int numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
