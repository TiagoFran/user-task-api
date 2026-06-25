package com.tiagofran.usuariotarefaapi.controller;

import com.tiagofran.usuariotarefaapi.dto.TarefaRequestDTO;
import com.tiagofran.usuariotarefaapi.dto.TarefaResponseDTO;
import com.tiagofran.usuariotarefaapi.entity.Tarefa;
import com.tiagofran.usuariotarefaapi.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    private TarefaResponseDTO converterParaDTO(Tarefa tarefa){
        return new TarefaResponseDTO(tarefa.getId(),tarefa.getTitulo(),tarefa.getDescricao(),tarefa.isConcluido());
    }

    @PostMapping("/usuarios/{usuarioId}/tarefas")
    public ResponseEntity<TarefaResponseDTO> criar(@PathVariable Long usuarioId, @Valid @RequestBody TarefaRequestDTO dto){

        Tarefa tarefa = new Tarefa();

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setConcluido(false);

        Tarefa tarefaSalva =  tarefaService.criarTarefa(usuarioId, tarefa);

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(tarefaSalva));
    }

    @GetMapping("/tarefas")
    public List<TarefaResponseDTO> listar(){

        List<Tarefa> tarefas = tarefaService.listarTarefas();

        List<TarefaResponseDTO> response = new ArrayList<>();

        for (Tarefa tarefa : tarefas) {
            response.add(converterParaDTO(tarefa));
        }

        return response;
    }

    @GetMapping("/tarefas/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long id){

        Optional<Tarefa> tarefa = tarefaService.buscarPorId(id);

        if(tarefa.isPresent()){
            return ResponseEntity.ok(converterParaDTO(tarefa.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios/{usuarioId}/tarefas")
    public List<TarefaResponseDTO> buscarPorUsuarioId(@PathVariable Long usuarioId){

        List<Tarefa> tarefas = tarefaService.buscarPorUsuarioId(usuarioId);

        List<TarefaResponseDTO> response = new ArrayList<>();

        for (Tarefa tarefa : tarefas) {
            response.add(converterParaDTO(tarefa));
        }

        return response;
    }

    @GetMapping("/tarefas/concluidas")
    public List<TarefaResponseDTO> buscarConcluidas(){

        List<Tarefa> tarefas = tarefaService.listarConcluidas();

        List<TarefaResponseDTO> response = new ArrayList<>();

        for (Tarefa tarefa : tarefas) {
            response.add(converterParaDTO(tarefa));
        }

        return response;
    }

    @GetMapping("/tarefas/pendentes")
    public List<TarefaResponseDTO> buscarPendentes(){

        List<Tarefa> tarefas = tarefaService.listarPendentes();

        List<TarefaResponseDTO> response = new ArrayList<>();

        for (Tarefa tarefa : tarefas) {
            response.add(converterParaDTO(tarefa));
        }

        return response;
    }

    @PatchMapping("/tarefas/{id}/concluir")
    public ResponseEntity<TarefaResponseDTO> marcarConcluida(@PathVariable Long id){

        Optional<Tarefa> tarefaEncontrada = tarefaService.marcarComoConcluida(id);

        if(tarefaEncontrada.isPresent()){
            return ResponseEntity.ok(converterParaDTO(tarefaEncontrada.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/tarefas/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TarefaRequestDTO dto){

        Tarefa tarefaAtualizada = new Tarefa();

        tarefaAtualizada.setTitulo(dto.titulo());
        tarefaAtualizada.setDescricao(dto.descricao());

        Optional<Tarefa> tarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);

        if (tarefa.isPresent()) {
            return ResponseEntity.ok(converterParaDTO(tarefa.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){

        boolean deletado = tarefaService.deletarTarefa(id);

        if(deletado){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
