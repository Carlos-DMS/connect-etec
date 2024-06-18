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

@RestController
@RequestMapping("/grupo")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity criarGrupo(@RequestHeader("Authorization") String authorizationHeader,
                                     @RequestBody @Valid CriarGrupoDto grupoDto){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            boolean criado = grupoService.criarGrupo(grupoDto, usuario.getLogin());
            if(criado){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/buscarPorId")
    public ResponseEntity<RespostaGrupoDto> buscarPorId(@RequestBody @Valid IdGrupoDto idGrupo){
        RespostaGrupoDto respostaDto = grupoService.buscarPorId(idGrupo);

        if (respostaDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(respostaDto);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity deletar(@RequestHeader("Authorization") String authorizationHeader,
                                  @RequestBody @Valid IdGrupoDto idGrupo){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if (usuario != null) {
            boolean deletado = grupoService.deletarGrupo(usuario.getLogin(), idGrupo);

            if(deletado){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/membros")
    public ResponseEntity<List<UserDetails>> todosMembros(@RequestBody @Valid IdGrupoDto idGrupo) {
        List<UserDetails> membros = grupoService.todosMembros(idGrupo);

        if(membros != null){
            return ResponseEntity.status(HttpStatus.OK).body(membros);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/tornarModerador")
    public ResponseEntity tornarModerador(@RequestHeader("Authorization") String authorizationHeader,
                                          @RequestBody @Valid IdGrupoDto idGrupo){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            boolean tornarMorderador = grupoService.tornarModerador(usuario.getLogin(), idGrupo);

            if(tornarMorderador){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/rebaixarModerador")
    public ResponseEntity rebaixarModerador(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody @Valid IdGrupoDto idGrupo){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            boolean rebaixarModerador = grupoService.rebaixarModerador(usuario.getLogin(), idGrupo);

            if(rebaixarModerador){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/entrarNoGrupo")
    public ResponseEntity<String> entrarNoGrupo(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody @Valid IdGrupoDto idGrupo){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            boolean entrarNoGrupo = grupoService.entrarNoGrupo(usuario.getLogin(), idGrupo);

            if(entrarNoGrupo){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/removerUsuario")
    public ResponseEntity removerUsuario(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody @Valid IdGrupoDto idGrupo){
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        if(usuario != null){
            boolean removerUsuario = grupoService.removerUsuario(usuario.getLogin(), idGrupo);

            if(removerUsuario){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
