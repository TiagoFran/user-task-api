package com.tiagofran.usuariotarefaapi.repository;

import com.tiagofran.usuariotarefaapi.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {

    List<Tarefa> findByUsuarioId(Long usuarioId);

    List<Tarefa> findByConcluidaTrue();

    List<Tarefa> findByConcluidaFalse();
}
