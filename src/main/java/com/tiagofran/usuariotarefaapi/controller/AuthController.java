package com.tiagofran.usuariotarefaapi.controller;

import com.tiagofran.usuariotarefaapi.dto.LoginRequestDTO;
import com.tiagofran.usuariotarefaapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO dto) {

        boolean sucesso = usuarioService.login(dto);

        if (sucesso) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
