package br.com.e_commerce.Zenk.dtos.request;

public record EnderecoRequestDTO(
        String logradouro,
        int numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
