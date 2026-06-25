package com.tiagofran.usuariotarefaapi.dto;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        List<TarefaResponseDTO> tarefas
) {
}
