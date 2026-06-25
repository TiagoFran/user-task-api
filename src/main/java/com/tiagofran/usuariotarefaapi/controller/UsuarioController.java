package com.tiagofran.usuariotarefaapi.controller;

import com.tiagofran.usuariotarefaapi.dto.TarefaResponseDTO;
import com.tiagofran.usuariotarefaapi.dto.UsuarioRequestDTO;
import com.tiagofran.usuariotarefaapi.dto.UsuarioResponseDTO;
import com.tiagofran.usuariotarefaapi.entity.Tarefa;
import com.tiagofran.usuariotarefaapi.entity.Usuario;
import com.tiagofran.usuariotarefaapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private UsuarioResponseDTO converterParaDTO(Usuario usuario) {

        List<TarefaResponseDTO> tarefasDTO = new ArrayList<>();

        for (Tarefa tarefa : usuario.getTarefas()) {

            tarefasDTO.add(new TarefaResponseDTO(tarefa.getId(),tarefa.getTitulo(),tarefa.getDescricao(),tarefa.isConcluido()));
        }
        return new UsuarioResponseDTO(usuario.getId(),usuario.getNome(),usuario.getEmail(),tarefasDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());

        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO(usuarioSalvo.getId(),usuarioSalvo.getNome(),usuarioSalvo.getEmail(),List.of());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        List<UsuarioResponseDTO> response = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            response.add(converterParaDTO(usuario));
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {

        Optional<Usuario> usuario = usuarioService.buscarPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(converterParaDTO(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {

        Usuario usuarioAtualizado = new Usuario();

        usuarioAtualizado.setNome(dto.nome());
        usuarioAtualizado.setEmail(dto.email());
        usuarioAtualizado.setSenha(dto.senha());

        Optional<Usuario> usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(converterParaDTO(usuario.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        boolean encontrado = usuarioService.deletarUsuario(id);

        if (encontrado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
