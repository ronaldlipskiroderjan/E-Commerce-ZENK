package br.com.e_commerce.Zenk.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUsuarioRequestDTO(
         @NotBlank String nome,
         @NotBlank @Email String email,
         @NotBlank String cpf,
         @NotBlank String telefone
) {
}