package com.maace.connectEtec.controllers;

import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public abstract class UsuarioController<T extends UsuarioModel, D> {

    private final UsuarioService<T> service;

    protected UsuarioController(UsuarioService<T> service) {
        this.service = service;
    }

    @PostMapping("/salvar")
    public ResponseEntity<T> salvar(@RequestBody @Valid D dto) {
        T entity = convertDtoToEntity(dto);
        service.salvar(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<T>> listarTodos() {
        List<T> entities = service.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<Boolean> validarUsuario(@RequestParam String login, @RequestParam String senha) {
        boolean valido = service.validarUsuario(login, senha);
        HttpStatus status = valido ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valido);
    }

    protected abstract T convertDtoToEntity(D dto);
}

