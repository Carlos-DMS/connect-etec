package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.comentario.CriarComentarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.ComentarioService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    @Autowired
    ComentarioService comentarioService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity criar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CriarComentarioDto comentarioDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if (usuario != null) {
            Boolean resposta = comentarioService.criar(usuario, UUID.fromString(comentarioDto.idPost()), comentarioDto);

            if (resposta) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
