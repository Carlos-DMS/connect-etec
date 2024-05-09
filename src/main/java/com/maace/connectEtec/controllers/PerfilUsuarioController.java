package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.*;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.PerfilUsuarioService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfilUsuario")
public class PerfilUsuarioController {

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @Autowired
    UsuarioService usuarioService;

    @PutMapping("/editar")
    public ResponseEntity editar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid EditarPerfilDto editarPerfilDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null) {
            perfilUsuarioService.editarPerfil(editarPerfilDto, usuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Optional<RespostaPerfilUsuarioDto>>> listarTodos() {
        List<Optional<RespostaPerfilUsuarioDto>> perfis = perfilUsuarioService.listarPerfis();

        if (perfis != null) {
            return ResponseEntity.status(HttpStatus.OK).body(perfis);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscarMeuPerfil")
    public ResponseEntity<AcessarPerfilUsuarioDto> buscarMeuPerfil(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        AcessarPerfilUsuarioDto acessarPerfilUsuarioDto = perfilUsuarioService.acessarPerfilUsuario(usuario.getLogin());

        if (acessarPerfilUsuarioDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(acessarPerfilUsuarioDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscarPerfil")
    public ResponseEntity<AcessarPerfilUsuarioDto> buscarPerfil(@RequestBody @Valid BuscarUsuarioDto usuarioDto) {
        AcessarPerfilUsuarioDto acessarPerfilUsuarioDto = perfilUsuarioService.acessarPerfilUsuario(usuarioDto.login());

        if (acessarPerfilUsuarioDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(acessarPerfilUsuarioDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscarMeusPosts")
    public ResponseEntity<List<Optional<RespostaPostDto>>> buscarMeusPosts(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        List<Optional<RespostaPostDto>> posts = perfilUsuarioService.buscarPosts(usuario.getLogin());

        if (!posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscarPosts")
    public ResponseEntity<List<Optional<RespostaPostDto>>> buscarPosts(@RequestBody @Valid BuscarUsuarioDto usuarioDto) {
        List<Optional<RespostaPostDto>> posts = perfilUsuarioService.buscarPosts(usuarioDto.login());

        if (!posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
