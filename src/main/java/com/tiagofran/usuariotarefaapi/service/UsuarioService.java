package com.tiagofran.usuariotarefaapi.service;

import com.tiagofran.usuariotarefaapi.dto.LoginRequestDTO;
import com.tiagofran.usuariotarefaapi.entity.Usuario;
import com.tiagofran.usuariotarefaapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario){
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new IllegalArgumentException("Erro: Usuario já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> atualizarUsuario(Long id, Usuario usuarioAtualizado){

        Optional<Usuario> usuarioEncontrado = buscarPorId(id);

        if(usuarioEncontrado.isPresent()){

            Usuario usuario = usuarioEncontrado.get();

            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());

            Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuarioAtualizado.getEmail());

            if(usuarioEmail.isPresent() && !usuarioEmail.get().getId().equals(id)){
                throw new IllegalArgumentException("Erro: Usuario já cadastrado.");
            }

            return Optional.of(usuarioRepository.save(usuario));
        }

        return Optional.empty();
    }

    public boolean deletarUsuario(Long id){

        Optional<Usuario> usuarioEncontrado = buscarPorId(id);

        if(usuarioEncontrado.isPresent()){
            usuarioRepository.delete(usuarioEncontrado.get());
            return true;
        }
        return false;
    }

    public boolean login(LoginRequestDTO dto){

        Optional<Usuario> usuario = usuarioRepository.findByEmail(dto.email());

        if(usuario.isPresent() && usuario.get().getSenha().equals(dto.senha())){

            return true;
        }

        return false;
    }
}
