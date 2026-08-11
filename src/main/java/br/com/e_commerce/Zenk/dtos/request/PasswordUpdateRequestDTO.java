package br.com.e_commerce.Zenk.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateRequestDTO(
       @NotBlank String senha
) {
}
