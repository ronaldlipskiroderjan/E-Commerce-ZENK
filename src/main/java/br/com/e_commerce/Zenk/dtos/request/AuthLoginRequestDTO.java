package br.com.e_commerce.Zenk.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String senha
) {
}
