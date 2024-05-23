package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.perfilGrupo.AcessarPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.RespostaPerfilGrupoDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.GrupoModel;
import com.maace.connectEtec.models.PerfilGrupoModel;
import com.maace.connectEtec.services.GrupoService;
import com.maace.connectEtec.services.PerfilGrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/perfisGrupo")
public class PerfilGrupoController {

    @Autowired
    PerfilGrupoService perfilGrupoService;

    @Autowired
    GrupoService grupoService;

    @GetMapping("/buscarPorId")
    public ResponseEntity<RespostaPerfilGrupoDto> buscarPorId(UUID idPerfilGrupo, UUID idGrupo){
        Optional<PerfilGrupoModel> perfilOptional = perfilGrupoService.buscarPorId(idPerfilGrupo);
        Optional<GrupoModel> grupoOptional = grupoService.buscarPorId(idGrupo);

        if(perfilOptional.isPresent() && grupoOptional.isPresent()){
            RespostaPerfilGrupoDto perfilGrupoDto = new RespostaPerfilGrupoDto(grupoOptional.get().getNome(), perfilOptional.get().getUrlFotoPerfil());
            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<RespostaPerfilGrupoDto>> listarTodos(){
        List<PerfilGrupoModel> perfisGrupoModel = perfilGrupoService.listarTodos();

        if(perfisGrupoModel != null) {
            List<RespostaPerfilGrupoDto> perfilGrupoDtos = new ArrayList<>();
            List<GrupoModel> grupoNomes = grupoService.listarTodos();

            int i = 0;
            for(PerfilGrupoModel perfilGrupoModel : perfisGrupoModel){
                perfilGrupoDtos.add(new RespostaPerfilGrupoDto(grupoNomes.get(i++).getNome(), perfilGrupoModel.getUrlFotoPerfil()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoDtos);

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/editar")
    public ResponseEntity editar(GrupoModel grupo,
                                 @RequestHeader("Authorization") String authorizationHeader,
                                 @RequestBody @Valid EditarPerfilGrupoDto editarPerfilGrupoDto){
        if(perfilGrupoService.editarDados(grupo, editarPerfilGrupoDto, authorizationHeader)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscarPerfil")
    public ResponseEntity buscarPerfilGrupo(UUID id){
        if(perfilGrupoService.buscarPerfilGrupo(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoService.buscarPerfilGrupo(id));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/acessarPerfil")
    public ResponseEntity<AcessarPerfilGrupoDto> acessarPerfilGrupo(UUID id){
        if(perfilGrupoService.acessarPerfilGrupo(id) != null){
            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoService.acessarPerfilGrupo(id));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscarPost")
    public ResponseEntity<List<Optional<RespostaPostDto>>> buscarPosts(UUID id){
        if(perfilGrupoService.buscarPosts(id) != null){
            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoService.buscarPosts(id));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
