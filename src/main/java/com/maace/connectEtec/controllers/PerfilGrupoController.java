package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.grupo.IdGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.AcessarPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarDadosPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarFotoPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.RespostaPerfilGrupoDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.PerfilGrupoService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfilGrupo")
public class PerfilGrupoController {

    @Autowired
    PerfilGrupoService perfilGrupoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/buscarPorId")
    public ResponseEntity<RespostaPerfilGrupoDto> buscarPorId(@RequestBody @Valid IdGrupoDto idGrupo){
        RespostaPerfilGrupoDto perfil = perfilGrupoService.buscarPorId(idGrupo);

        if(perfil != null){
            return ResponseEntity.status(HttpStatus.OK).body(perfil);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<RespostaPerfilGrupoDto>> listarTodos(){
        List<RespostaPerfilGrupoDto> perfisDto = perfilGrupoService.listarTodos();

        if(!perfisDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(perfisDto);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/buscarPosts")
    public ResponseEntity<List<Optional<RespostaPostDto>>> buscarPosts(@RequestBody @Valid IdGrupoDto idGrupo){
        List<Optional<RespostaPostDto>> postDto = perfilGrupoService.buscarPosts(idGrupo);

        if(postDto != null || !postDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(postDto);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/editarDados")
    public ResponseEntity editarDados(@RequestHeader("Authorization") String authorizationHeader,
                                      @RequestBody @Valid EditarDadosPerfilGrupoDto editarDadosPerfilGrupoDto){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(perfilGrupoService.editarDados(editarDadosPerfilGrupoDto, usuario)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/editarFoto")
    public ResponseEntity editarFoto(@RequestHeader("Authorization") String authorizationHeader,
                                     @RequestBody @Valid EditarFotoPerfilGrupoDto editarFotoPerfilGrupoDto){
        if(perfilGrupoService.editarFotoPerfil(editarFotoPerfilGrupoDto, authorizationHeader)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/acessarPerfil")
    public ResponseEntity<AcessarPerfilGrupoDto> acessarPerfilGrupo(@RequestBody @Valid IdGrupoDto idGrupo){
        AcessarPerfilGrupoDto acessoDto = perfilGrupoService.acessarPerfilGrupo(idGrupo);

        if(acessoDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(acessoDto);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}




































