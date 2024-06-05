package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.perfilUsuario.*;
import com.maace.connectEtec.dtos.usuario.BuscarUsuarioDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
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

    @PatchMapping("/editarDados")
    public ResponseEntity editarDados(@RequestHeader("Authorization") String authorizationHeader,
                                      @RequestBody @Valid EditarPerfilUsuarioDto editarPerfilDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null) {
            perfilUsuarioService.editarDadosPerfil(editarPerfilDto, usuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/editarFotoPerfil")
    public ResponseEntity editarFotoPerfil(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody @Valid EditarFotoPerfilDto editarFotoPerfilDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null) {
            perfilUsuarioService.editarFotoPerfil(editarFotoPerfilDto, usuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<Optional<RespostaPerfilUsuarioDto>>> listarTodos(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if (usuario != null) {
            List<Optional<RespostaPerfilUsuarioDto>> perfis = perfilUsuarioService.listarPerfis(usuario);

            if (perfis != null) {
                return ResponseEntity.status(HttpStatus.OK).body(perfis);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/buscarMeuPerfil")
    public ResponseEntity<AcessarPerfilUsuarioDto> buscarMeuPerfil(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        AcessarPerfilUsuarioDto acessarPerfilUsuarioDto = perfilUsuarioService.acessarPerfilUsuario(usuario.getLogin());

        if (acessarPerfilUsuarioDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(acessarPerfilUsuarioDto);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscarPerfil")
    public ResponseEntity<AcessarPerfilUsuarioDto> buscarPerfil(@RequestBody @Valid BuscarUsuarioDto usuarioDto) {
        AcessarPerfilUsuarioDto acessarPerfilUsuarioDto = perfilUsuarioService.acessarPerfilUsuario(usuarioDto.login());

        if (acessarPerfilUsuarioDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(acessarPerfilUsuarioDto);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscarMeusPosts")
    public ResponseEntity<List<Optional<RespostaPostDto>>> buscarMeusPosts(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        List<Optional<RespostaPostDto>> posts = perfilUsuarioService.buscarPosts(usuario.getLogin());

        if (!posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscarPosts")
    public ResponseEntity<List<Optional<RespostaPostDto>>> buscarPosts(@RequestBody @Valid BuscarUsuarioDto usuarioDto) {
        List<Optional<RespostaPostDto>> posts = perfilUsuarioService.buscarPosts(usuarioDto.login());

        if (!posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/seguir")
    public ResponseEntity<Boolean> seguirUsuario (
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid SeguirUsuarioDto seguirUsuarioDto)
    {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        Boolean estadoSeguir = perfilUsuarioService.seguirUsuario(
                usuario.getLogin(),
                seguirUsuarioDto.loginUsuarioSeguido(),
                seguirUsuarioDto.estaSeguido()
        );

        if (estadoSeguir != null) {
            return ResponseEntity.status(HttpStatus.OK).body(estadoSeguir);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
