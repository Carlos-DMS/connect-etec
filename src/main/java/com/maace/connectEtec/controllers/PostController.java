package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.post.CriarPostDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.PostService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CriarPostDto criarPostDto){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if (usuario != null) {
            postService.criar(criarPostDto, usuario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
