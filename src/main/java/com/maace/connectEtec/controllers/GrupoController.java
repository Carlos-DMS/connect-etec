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

    @PostMapping("/criar")//separa pra criar o grupo (nome e id perfil) e so editar o resto no perfilGrupo
    public ResponseEntity criarGrupo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CriarGrupoDto grupoDto){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            grupoService.criarGrupo(grupoDto, usuario.getLogin());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/buscarPorId")
    public ResponseEntity<RespostaGrupoDto> buscarPorId(@RequestBody @Valid IdGrupoDto grupoDto){
        RespostaGrupoDto respostaDto = grupoService.buscarPorId(UUID.fromString(grupoDto.id()));

        if (respostaDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(respostaDto);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/listarTodos")//manda essa versão pra perfilGrupo
    public ResponseEntity<List<RespostaGrupoDto>> listarTodos() {
        List<RespostaGrupoDto> gruposDto = grupoService.listarTodos();
        if(gruposDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(gruposDto);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity deletar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid IdGrupoDto idGrupo){
        boolean deletado = grupoService.deletarGrupo(UUID.fromString(idGrupo.id()), authorizationHeader);
        if(deletado){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/membros")
    public ResponseEntity<List<UserDetails>> todosMembros(@RequestBody @Valid IdGrupoDto id) {
        List<UserDetails> membros = grupoService.todosMembros(UUID.fromString(id.id()));
        if(membros != null) return ResponseEntity.status(HttpStatus.OK).body(membros);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/tornarModerador")
    public ResponseEntity tornarModerador(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid IdGrupoDto idGrupo){
        if(grupoService.tornarModerador(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/rebaixarModerador")
    public ResponseEntity rebaixarModerador(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid IdGrupoDto idGrupo){
        if(grupoService.rebaixarModerador(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/entrarNoGrupo")
    public ResponseEntity<String> entrarNoGrupo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid IdGrupoDto idGrupo){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(grupoService.entrarNoGrupo(usuario.getLogin(), UUID.fromString(idGrupo.id()))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/removerUsuario")
    public ResponseEntity removerUsuario(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid IdGrupoDto idGrupo){
        if(grupoService.removerUsuario(authorizationHeader, idGrupo)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
