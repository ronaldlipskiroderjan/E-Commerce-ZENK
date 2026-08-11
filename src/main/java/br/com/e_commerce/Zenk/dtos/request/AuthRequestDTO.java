package br.com.e_commerce.Zenk.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
         @NotBlank String nome,
         @Email @NotBlank String email,
         @NotBlank String senha,
         @NotBlank String cpf,
         @NotBlank String telefone
) {
}
