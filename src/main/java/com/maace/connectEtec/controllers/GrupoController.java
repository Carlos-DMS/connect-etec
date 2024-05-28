package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.grupo.IdGrupoDto;
import com.maace.connectEtec.dtos.grupo.CriarGrupoDto;
import com.maace.connectEtec.dtos.grupo.RespostaGrupoDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.GrupoService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/grupo")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criarGrupo")
    public ResponseEntity criarGrupo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CriarGrupoDto grupoDto){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            grupoService.criarGrupo(grupoDto, authorizationHeader);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/buscarPorId")
    public ResponseEntity<RespostaGrupoDto> buscarPorId(IdGrupoDto grupoDto){
        RespostaGrupoDto respostaDto = grupoService.buscarPorId(UUID.fromString(grupoDto.id()));

        return ResponseEntity.status(HttpStatus.OK).build();
    }  //TESTANDO...!!!

    @GetMapping("/listarTodos")
    public ResponseEntity<List<RespostaGrupoDto>> listarTodos() {
        List<RespostaGrupoDto> gruposDto = grupoService.listarTodos();
        if(gruposDto != null) return ResponseEntity.status(HttpStatus.OK).body(gruposDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity deletar(IdGrupoDto idGrupo, @RequestHeader("Authorization") String authorizationHeader){
        boolean deletado = grupoService.deletarGrupo(UUID.fromString(idGrupo.id()), authorizationHeader);
        if(deletado) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/membros")
    public ResponseEntity<List<UserDetails>> todosMembros(IdGrupoDto id) {
        List<UserDetails> membros = grupoService.todosMembros(UUID.fromString(id.id()));
        if(membros != null) return ResponseEntity.status(HttpStatus.OK).body(membros);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/tornarModerador")
    public ResponseEntity tornarModerador(@RequestHeader("Authorization") String authorizationHeader, IdGrupoDto idGrupo){
        if(grupoService.tornarModerador(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/rebaixarModerador")
    public ResponseEntity rebaixarModerador(@RequestHeader("Authorization") String authorizationHeader, IdGrupoDto idGrupo){
        if(grupoService.rebaixarModerador(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/adicionarUsuario")
    public ResponseEntity adicionarUsuario(@RequestHeader("Authorization") String authorizationHeader, IdGrupoDto idGrupo){
        if(grupoService.adicionarUsuario(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/removerUsuario")
    public ResponseEntity removerUsuario(@RequestHeader("Authorization") String authorizationHeader, IdGrupoDto idGrupo){
        if(grupoService.removerUsuario(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
