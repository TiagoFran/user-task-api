package com.tiagofran.usuariotarefaapi.dto;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        boolean concluido
) {
}
