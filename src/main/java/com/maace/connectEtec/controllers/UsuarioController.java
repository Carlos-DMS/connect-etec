package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.UsuarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioDto usuarioDto){

        UsuarioModel usuario = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDto, usuario);
        service.salvar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos(){

        List<UsuarioModel> usuarios = service.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<Boolean> validarUsuario(@RequestParam String login, @RequestParam String senha){

        boolean valido = service.validarUsuario(login, senha);

        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valido);
    }
}
