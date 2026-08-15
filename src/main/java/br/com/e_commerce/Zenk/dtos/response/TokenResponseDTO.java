package br.com.e_commerce.Zenk.dtos.response;

public record TokenResponseDTO(
        String token,
        long expiresIn
) {
}
