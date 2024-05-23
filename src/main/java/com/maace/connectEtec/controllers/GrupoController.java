package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.grupo.RespostaGrupoDto;
import com.maace.connectEtec.models.GrupoModel;
import com.maace.connectEtec.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/grupo")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping("/buscarPerfil")
    public ResponseEntity<RespostaGrupoDto> buscarPorId(UUID id){
        Optional<GrupoModel> grupoOptional = grupoService.buscarPorId(id);
        if(grupoOptional.isPresent()){
            RespostaGrupoDto grupoDto = new RespostaGrupoDto(grupoOptional.get().getIdGrupo(), grupoOptional.get().getNome(), grupoOptional.get().getIdPerfilGrupo());
            return ResponseEntity.status(HttpStatus.OK).body(grupoDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<RespostaGrupoDto>> listarTodos() {
        List<GrupoModel> grupos = grupoService.listarTodos();
        if(grupos != null){
            List<RespostaGrupoDto> grupoDtos = new ArrayList<>();
            for(GrupoModel grupo : grupos){
                grupoDtos.add(new RespostaGrupoDto(grupo.getIdGrupo(), grupo.getNome(), grupo.getIdPerfilGrupo()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(grupoDtos);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(UUID id, String nome){
        boolean atulizado = grupoService.atualizarGrupo(id, nome);
        if(atulizado) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletar(@PathVariable UUID id, String userToken){
        boolean deletado = grupoService.deletarGrupo(id, userToken);
        if(deletado) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}/usuarios")
    public ResponseEntity<List<UserDetails>> todosUsuarios(@PathVariable UUID id){
        List<UserDetails> usuarios = grupoService.todosUsuarios(id);
        if(usuarios != null) return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}/admins")
    public ResponseEntity<List<UserDetails>> todosAdmins(@PathVariable UUID id) {
        List<UserDetails> admins = grupoService.todosAdmins(id);
        if(admins != null) return ResponseEntity.status(HttpStatus.OK).body(admins);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}/membros")
    public ResponseEntity<List<UserDetails>> todosMembros(@PathVariable UUID id) {
        List<UserDetails> membros = grupoService.todosMembros(id);
        if(membros != null) return ResponseEntity.status(HttpStatus.OK).body(membros);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
