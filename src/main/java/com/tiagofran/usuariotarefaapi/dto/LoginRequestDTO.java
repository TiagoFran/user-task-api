package com.tiagofran.usuariotarefaapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @Email(message = "Email inválido.")
        @NotBlank(message = "Email obrigatório.")
        String email,

        @NotBlank(message = "Senha obrigatória.")
        String senha
) {
}
