package com.tiagofran.usuariotarefaapi.dto;

import jakarta.validation.constraints.NotBlank;

public record TarefaRequestDTO(

        @NotBlank(message = "Título obrigatório")
        String titulo,

        @NotBlank(message = "Descrição obrigatória")
        String descricao
) {
}


