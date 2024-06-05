package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.comentario.CriarComentarioDto;
import com.maace.connectEtec.dtos.comentario.CurtirComentarioDto;
import com.maace.connectEtec.dtos.comentario.RespostaComentarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.ComentarioService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    @Autowired
    ComentarioService comentarioService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity criar
            (@RequestHeader("Authorization") String authorizationHeader,
             @RequestBody @Valid CriarComentarioDto comentarioDto)
    {
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

    @GetMapping
    public ResponseEntity<List<RespostaComentarioDto>> listarComentarios
            (@RequestHeader("Authorization") String authorizationHeader,
             @RequestParam(name = "idPost", required = true) String idPost)
    {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if (usuario != null) {
            List<RespostaComentarioDto> comentarios = comentarioService.listarComentarios(usuario, UUID.fromString(idPost));

            if (!comentarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(comentarios);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/curtir")
    public ResponseEntity curtir (@RequestHeader("Authorization") String authorizationHeader,
                                  @RequestBody @Valid CurtirComentarioDto curtirComentarioDto)
    {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        Boolean estadoLike = comentarioService.curtir(
                usuario.getLogin(),
                UUID.fromString(curtirComentarioDto.idComentario()),
                curtirComentarioDto.estaCurtido()
        );

        if (estadoLike != null) {
            return ResponseEntity.status(HttpStatus.OK).body(estadoLike);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
