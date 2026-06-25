package com.tiagofran.usuariotarefaapi.service;

import com.tiagofran.usuariotarefaapi.entity.Tarefa;
import com.tiagofran.usuariotarefaapi.entity.Usuario;
import com.tiagofran.usuariotarefaapi.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioService usuarioService;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioService usuarioService) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioService = usuarioService;
    }

    public Tarefa criarTarefa(Long usuarioId, Tarefa tarefa){

        Optional<Usuario> buscarUsuario = usuarioService.buscarPorId(usuarioId);

        if(buscarUsuario.isPresent()){

            tarefa.setUsuario(buscarUsuario.get());
            return tarefaRepository.save(tarefa);
        }

        throw new IllegalArgumentException("Erro: Usuario não encontrado.");
    }

    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id){
        return tarefaRepository.findById(id);
    }

    public List<Tarefa> buscarPorUsuarioId(Long usuarioId){
        return tarefaRepository.findByUsuarioId(usuarioId);
    }

    public List<Tarefa> listarConcluidas(){
        return tarefaRepository.findByConcluidaTrue();
    }

    public List<Tarefa> listarPendentes(){
        return tarefaRepository.findByConcluidaFalse();
    }

    public Optional<Tarefa> marcarComoConcluida(Long id){

        Optional<Tarefa> tarefaPendente = buscarPorId(id);

        if(tarefaPendente.isPresent()){
            Tarefa tarefa = tarefaPendente.get();

            tarefa.setConcluido(true);
            return Optional.of(tarefaRepository.save(tarefa));
        }

        return Optional.empty();
    }

    public Optional<Tarefa> atualizarTarefa(Long id, Tarefa tarefaAtualizada){

        Optional<Tarefa> encontrada = buscarPorId(id);

        if(encontrada.isPresent()){

            Tarefa tarefa = encontrada.get();

            tarefa.setTitulo(tarefaAtualizada.getTitulo());
            tarefa.setDescricao(tarefaAtualizada.getDescricao());

            return Optional.of(tarefaRepository.save(tarefa));
        }

        return Optional.empty();
    }

    public boolean deletarTarefa(Long id){

        Optional<Tarefa> tarefa = buscarPorId(id);

        if(tarefa.isPresent()){
            tarefaRepository.delete(tarefa.get());
            return true;
        }

        return false;
    }
}
