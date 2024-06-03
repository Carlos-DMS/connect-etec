package com.maace.connectEtec.controllers;


import com.maace.connectEtec.dtos.post.CriarPostDto;
import com.maace.connectEtec.dtos.post.CurtirPostDto;
import com.maace.connectEtec.dtos.post.IdPostDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.PostService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CriarPostDto criarPostDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if (usuario != null) {
            postService.criar(criarPostDto, usuario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping()
    public ResponseEntity<List<Optional<RespostaPostDto>>> listarTodos(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);
        List<Optional<RespostaPostDto>> respostaPostDtos = postService.listarPosts(usuario);

        if (!respostaPostDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(respostaPostDtos);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/curtir")
    public ResponseEntity<Boolean> curtirPost(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CurtirPostDto curtirPostDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        Boolean estadoLike = postService.curtir(usuario.getLogin(), UUID.fromString(curtirPostDto.idPost()), curtirPostDto.estaCurtido());

        if (estadoLike != null) {
            return ResponseEntity.status(HttpStatus.OK).body(estadoLike);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping()
    public ResponseEntity deletarPost(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid IdPostDto idPostDto) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        Boolean resposta = postService.deletarPost(usuario, UUID.fromString(idPostDto.idPost()));

        if (resposta != null) {
            if (resposta) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}